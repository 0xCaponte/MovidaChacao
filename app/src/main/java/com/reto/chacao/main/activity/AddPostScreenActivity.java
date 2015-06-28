package com.reto.chacao.main.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infteh.comboseekbar.ComboSeekBar;

import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.reto.chacao.R;
import com.reto.chacao.beans.Category;
import com.reto.chacao.main.adapter.CategorySpinnerAdapter;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;
import com.reto.chacao.util.StringUtil;

/**
 * Created by ULISES HARRIS on 01/06/2015.
 */
public class AddPostScreenActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    public static final String TAG = "AddPost-Activity";

    public static final int MAIN_IMAGE = 0;
    public static final int SECOND_IMAGE = 1;
    public static final int THIRD_IMAGE = 2;

    public int IMAGE_SELECTED = MAIN_IMAGE;

    public static final int REQUEST_CAMERA = 900;
    public static final int SELECT_FILE = 901;

    private RelativeLayout mMainPicture;
    private RelativeLayout mSecondPicture;
    private RelativeLayout mThirdPicture;
    private EditText mPostTitle;
    private Spinner mPostCategory;
    private EditText mPostZipCode;
    private EditText mPostPrice;
    private EditText mPostOriginalPrice;
    private EditText mPostDescription;
    //    private SeekBar mPostConditionItem;
    private Button mAddPost;
    private ImageButton mClosePost;
    private ProgressDialog mProgressDialog;
    private ArrayList<Category> mCategories;
    private CategorySpinnerAdapter mCategoryAdapter;
    private ComboSeekBar mPostConditionItem;
    private Spinner mCategorySpinner;
    private String mTitle;
    private String mLocation;
    private String mPrice;
    private String mOriginalPrice;
    private String mDescription;
    private int mCondition;
    private Category mCategory;
    private ImageButton mHelp;

    private ImageView mMainImage;
    private ImageView mSecondImage;
    private ImageView mThirdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_post_toolbar);
        toolbar.setTitle("");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        setViews();
        setFields();
        getCategories();
    }

    private void startDialog(String message) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    private void dismissDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private void getCategories() {

//        startDialog("Getting categories...");
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {
                    Log.i(TAG, "Success");
                    Gson gson = new Gson();
                    JSONObject message = jsonObject.optJSONObject("message");
                    mCategories = gson.fromJson(message.optString("categories"),
                            new TypeToken<ArrayList<Category>>() {
                            }.getType());
                    mCategoryAdapter = new CategorySpinnerAdapter(AddPostScreenActivity.this, mCategories);
                    // apply the Adapter:
                    mCategorySpinner.setAdapter(mCategoryAdapter);
                    mCategorySpinner.setEnabled(true);
//                    dismissDialog();
                } else {
//                    dismissDialog();
                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to log", volleyError);
                volleyError.printStackTrace();
//                dismissDialog();
            }
        };

        HashMap<String, String> parameters = new HashMap<String, String>();

        parameters.put(ClamourApiValues.GROUP_ID, "2");

        String url = ClamourApiValues.createStaticUrlGet(ClamourApiValues.TYPE_CATEGORY, parameters);

        Log.i(TAG, url);

        JsonObjectRequestUtil request = new JsonObjectRequestUtil(Request.Method.GET, url, null, responseListener, errorListener);

        MySingletonUtil.getInstance(this).addToRequestQueue(request);

    }

    private void setFields() {
        mPostTitle = (EditText) findViewById(R.id.post_title);
        mPostZipCode = (EditText) findViewById(R.id.post_location);
        mPostPrice = (EditText) findViewById(R.id.post_price);
        mPostPrice.addTextChangedListener(new CurrencyTextWatcher());
        mPostOriginalPrice = (EditText) findViewById(R.id.post_original_price);
        mPostOriginalPrice.addTextChangedListener(new CurrencyTextWatcher());
        mPostDescription = (EditText) findViewById(R.id.post_description);
        mCategorySpinner = (Spinner) findViewById(R.id.post_category);
        mCategorySpinner.setEnabled(false);
        mCategorySpinner.setOnItemSelectedListener(this);

        mPostConditionItem = (ComboSeekBar) findViewById(R.id.seekbar);
        List<String> seekBarStep = Arrays.asList("Brand new", "Like new", "Very good", "Good");
        mPostConditionItem.setAdapter(seekBarStep);
        mPostConditionItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCondition = position;
            }
        });
    }

    private void setViews() {
        mMainPicture = (RelativeLayout) findViewById(R.id.main_image);
        mMainPicture.setOnClickListener(this);

        mSecondPicture = (RelativeLayout) findViewById(R.id.second_image);
        mSecondPicture.setOnClickListener(this);

        mThirdPicture = (RelativeLayout) findViewById(R.id.third_image);
        mThirdPicture.setOnClickListener(this);

        mAddPost = (Button) findViewById(R.id.btn_post_item);
        mAddPost.setOnClickListener(this);

        mClosePost = (ImageButton) findViewById(R.id.add_post_btn_close);
        mClosePost.setOnClickListener(this);

        mHelp = (ImageButton) findViewById(R.id.add_post_btn_help);
        mHelp.setOnClickListener(this);

        mMainImage = (ImageView) findViewById(R.id.main_image_result);
        mMainImage.setOnClickListener(this);

        mSecondImage = (ImageView) findViewById(R.id.second_image_result);
        mSecondImage.setOnClickListener(this);

        mThirdImage = (ImageView) findViewById(R.id.third_image_result);
        mThirdImage.setOnClickListener(this);
    }

    private static Uri outputFileUri;

    private void openImageIntent() {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = AppUtil.getUniqueImageFilename();
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, SELECT_FILE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_image:
                IMAGE_SELECTED = MAIN_IMAGE;
                openImageIntent();
                break;
            case R.id.second_image:
                IMAGE_SELECTED = SECOND_IMAGE;
                openImageIntent();
                break;
            case R.id.third_image:
                IMAGE_SELECTED = THIRD_IMAGE;
                openImageIntent();
                break;
            case R.id.btn_post_item:
                onPostItem();
                break;
            case R.id.add_post_btn_close:
                onBackPressed();
                AppUtil.hideKeyboard(this);
                break;
            case R.id.add_post_btn_help:
                AppUtil.sendIntentEmail(this, "Question for Clamour");
                break;
        }

    }

    private void onPostItem() {
        mTitle = mPostTitle.getText().toString().trim();
        mLocation = mPostZipCode.getText().toString().trim();
        mPrice = mPostPrice.getText().toString().trim();
        mOriginalPrice = mPostOriginalPrice.getText().toString().trim();
        mDescription = mPostDescription.getText().toString().trim();

        if (validateFields()) {
            AppUtil.showAToast("Show Fee dialog");
        }

    }

    private boolean validateFields() {
        boolean valid = true;
        if ("".equals(mTitle)) {
            mPostTitle.setError("This field is required");
            return false;
        } else if ("".equals(mLocation) & !StringUtil.isValidNumber(mLocation)) {
            mPostZipCode.setError("This field is required");
            return false;
        } else if (mCategory == null) {
            AppUtil.showAToast("You need to select a category for the item");
            return false;
        } else if ("".equals(mPrice)) {
            mPostPrice.setError("This field is required");
            return false;
        } else if ("".equals(mDescription)) {
            mPostDescription.setError("This field is required");
            return false;
        }

        return valid;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_FILE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
                Bitmap myImg;
                switch (IMAGE_SELECTED) {
                    case MAIN_IMAGE:
                        myImg = BitmapFactory.decodeFile(selectedImageUri.getPath());
                        mMainImage.setImageBitmap(myImg);
                        mMainImage.setVisibility(View.VISIBLE);
                        break;
                    case SECOND_IMAGE:
                        myImg = BitmapFactory.decodeFile(selectedImageUri.getPath());
                        mSecondImage.setImageBitmap(myImg);
                        mSecondImage.setVisibility(View.VISIBLE);
                        break;
                    case THIRD_IMAGE:
                        myImg = BitmapFactory.decodeFile(selectedImageUri.getPath());
                        mThirdImage.setImageBitmap(myImg);
                        mThirdImage.setVisibility(View.VISIBLE);
                        break;
                    default:
                        myImg = BitmapFactory.decodeFile(selectedImageUri.getPath());
                        mMainImage.setImageBitmap(myImg);
                        mMainImage.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategory = mCategories.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//            if (requestCode == REQUEST_CAMERA) {
//                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//                File destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
////                ivImage.setImageBitmap(thumbnail);
//
//            } else if (requestCode == SELECT_FILE) {
//                Uri selectedImageUri = data.getData();
//                String[] projection = {MediaStore.MediaColumns.DATA};
//                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
//                        null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//                cursor.moveToFirst();
//
//                String selectedImagePath = cursor.getString(column_index);
//
//                Bitmap bm;
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(selectedImagePath, options);
//                final int REQUIRED_SIZE = 200;
//                int scale = 1;
//                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//                    scale *= 2;
//                options.inSampleSize = scale;
//                options.inJustDecodeBounds = false;
//                bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
////                ivImage.setImageBitmap(bm);
//            }
//        }
}

class CurrencyTextWatcher implements TextWatcher {

    boolean mEditing;

    public CurrencyTextWatcher() {
        mEditing = false;
    }

    public synchronized void afterTextChanged(Editable s) {
        if (!mEditing) {
            mEditing = true;

            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            try {
                String formatted = nf.format(Double.parseDouble(digits) / 100);
                s.replace(0, s.length(), formatted);
            } catch (NumberFormatException nfe) {
                s.clear();
            }

            mEditing = false;
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}