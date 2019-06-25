package com.devaj.antonio.combustivel.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.DataBase.DAO.Implement.UsuarioDaoImpl;
import com.devaj.antonio.combustivel.Model.RespostaUsuario;
import com.devaj.antonio.combustivel.Model.Usuario;
import com.devaj.antonio.combustivel.R;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String TAG = "COMBUSTIVEL";
    private CoordinatorLayout coordinatorLayout;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText matriculaAuto;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //coordinatorLayout = findViewById(R.id.coordinatorLogin);
        // Set up the login form.
        matriculaAuto = (AutoCompleteTextView) findViewById(R.id.matricula);
//        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.logarOuRegistrar);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();


            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

//    @Override
//    protected void onResume() {
//        if(getIntent().getBooleanExtra("SAIR",false)){
//            finish();
//        }
//        super.onResume();
//    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("SAIR", true);
        startActivity(i);

        super.onBackPressed();
    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(matriculaAuto, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        matriculaAuto.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String matricula = this.matriculaAuto.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError("Esta senha é muito curta");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid matricula address.
        if (TextUtils.isEmpty(matricula)) {
//            matricula.setError(getString(R.string.error_field_required));
            this.matriculaAuto.setError("Matricula é requerida.");
            focusView = this.matriculaAuto;
            cancel = true;
        }
//        } else if (!isEmailValid(matricula)) {
//            matricula.setError(getString(R.string.error_invalid_email));
//            focusView = matricula;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(matricula, password);
//            mAuthTask.execute((Void) null);
            String token = FirebaseInstanceId.getInstance().getToken();
            logarOuRegistrarRetrofit(matricula, password, token);
        }
    }

    private void logarOuRegistrarRetrofit(String matricula, String senha, String token) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SOService service = retrofit.create(SOService.class);
        Call<RespostaUsuario> registrarCall = service.registrar(matricula, senha, token);

        registrarCall.enqueue(new Callback<RespostaUsuario>() {
                  @Override
                  public void onResponse(Call<RespostaUsuario> call, Response<RespostaUsuario> response) {
                      showProgress(false);
                      //Log.i(TAG,"AQ: "+response.body().usuario.getNome());
                      if(!response.body().isErro()){
                          UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl(getApplication());

//                          usuarioDao.apagarTabela();


                          Usuario u = new Usuario();
                          u.setMatricula(response.body().usuario.getMatricula());
                          u.setNome(response.body().usuario.getNome());
                          u.setSenha(response.body().usuario.getSenha());

                          Log.i(TAG, "inserir: "+usuarioDao.inserirUsuario(u));
                          Toast.makeText(getApplication(), response.body().getMensagem(), Toast.LENGTH_SHORT).show();

                          //Log.i(TAG,""+response.body().usuario.getNome());
                          finish();

                          Intent i = new Intent(getApplicationContext(), MainActivity.class);
                          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(i);

                      }else{
//                          Snackbar.make(coordinatorLayout, response.body().getMensagem(), Snackbar.LENGTH_LONG)
//                                  .setAction("Action", null).show();
//                          Log.i(TAG,response.body().getMensagem());
                          Toast.makeText(getApplication(), response.body().getMensagem(), Toast.LENGTH_SHORT).show();
                      }

                  }

                  @Override
                  public void onFailure(Call<RespostaUsuario> call, Throwable t) {
                      showProgress(false);
//                      Snackbar.make(coordinatorLayout, "Falha ao realizar login.", Snackbar.LENGTH_LONG)
//                              .setAction("Action", null).show();
                      Toast.makeText(getApplication(),"Falha ao realizar login.", Toast.LENGTH_SHORT).show();

                  }
              }
        );


//        Call<RespostaInformacoes> respostaInformacoesCall =  service.getRespostaInformacoes();
//
//        respostaInformacoesCall.enqueue(new Callback<RespostaInformacoes>() {
//            @Override
//            public void onResponse(Call<RespostaInformacoes> call, Response<RespostaInformacoes> response) {
//                if(response.isSuccessful()){
//                    showProgress(false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespostaInformacoes> call, Throwable t) {
//                showProgress(false);
//            }
//        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
////        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }
//
////    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
////        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
////        ArrayAdapter<String> adapter =
////                new ArrayAdapter<>(LoginActivity.this,
////                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
////
////        matriculaAuto.setAdapter(adapter);
////    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

