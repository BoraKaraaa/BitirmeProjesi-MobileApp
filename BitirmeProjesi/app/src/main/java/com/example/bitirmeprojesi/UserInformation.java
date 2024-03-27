package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UserInformation extends AppCompatActivity
{
    EditText userName, age, height, weight, allergen, extraInformation;
    Spinner genderSpinner, goalSpinner, activityLevelSpinner, dietPreferenceSpinner,
            countrySpinner, stateSpinner;
    Button saveUserInformation, logOutButton;

    SharedPreferences userPref;
    GoogleMySQLDataBase googleMySQLDataBase;
    JSONHelper<UserData> userDataJSONHelper;

    private List<Country> countryList;
    private List<RegionData> regionDataList;
    private List<GoalData> goalDataList;
    private List<DietPreferenceData> dietPreferenceDataList;
    private List<AllergenData> allergenDataList;
    private List<UserAllergyData> userAllergyDataList;

    private boolean externalStateSelection = false;

    private UserData userData;

    private final int TOTAL_GET_DATA_AMOUNT = 5;
    private int totalGatheredData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        googleMySQLDataBase = new GoogleMySQLDataBase(this);
        userDataJSONHelper = new JSONHelper<UserData>(JSONHelper.JSONDictionary.toString(DataTables.User));

        initViews();

        initCountryValues();
        initGoalSpinnerValues();
        initDietPreferenceSpinnerValues();
        initAllergenValues();

        setCountrySpinnerListener();
        setSaveButtonListener();
        setLogOutButtonListener();
    }

    private void initViews()
    {
        saveUserInformation = findViewById(R.id.save_user_information_button);
        logOutButton = findViewById(R.id.log_out_button);

        userName = findViewById(R.id.nameEditText);
        age = findViewById(R.id.ageEditText);
        height = findViewById(R.id.heightEditText);
        weight = findViewById(R.id.weightEditText);
        allergen = findViewById(R.id.allergenEditText);

        genderSpinner = findViewById(R.id.genderSpinner);
        goalSpinner = findViewById(R.id.goalSpinner);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);
        dietPreferenceSpinner = findViewById(R.id.dietPreferenceSpinner);
        countrySpinner = findViewById(R.id.countrySpinner);
        stateSpinner = findViewById(R.id.stateSpinner);

        extraInformation = findViewById(R.id.extraInformationEditText);
    }

    private void checkAllDataGathered()
    {
        if(++totalGatheredData == TOTAL_GET_DATA_AMOUNT)
        {
            initUserInformation();
            totalGatheredData = 0;
        }
    }

    private void initCountryValues()
    {
        googleMySQLDataBase.getTable(DataTables.Region, RegionData.class, new DataBase.MultipleDataCallback<RegionData>()
        {
            @Override
            public void onResponse(List<RegionData> dataList)
            {
                regionDataList = dataList;
                checkAllDataGathered();
            }
        });

        countryList = loadCountriesFromJson();

        List<String> countryNames = new ArrayList<>();
        countryNames.add("None");

        for (Country country : countryList)
        {
            countryNames.add(country.getName());
        }

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryNames);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
    }

    private void initGoalSpinnerValues()
    {
        googleMySQLDataBase.getTable(DataTables.Goal, GoalData.class, new DataBase.MultipleDataCallback<GoalData>()
        {
            @Override
            public void onResponse(List<GoalData> dataList)
            {
                goalDataList = dataList;

                List<String> goalNames = new ArrayList<>();
                goalNames.add("None");

                for (GoalData goalData : dataList)
                {
                    String goalName = goalData.getGoalName();

                    if (goalName != null)
                    {
                        goalNames.add(goalName);
                    }
                }

                ArrayAdapter<String> goalAdapter = new ArrayAdapter<>(UserInformation.this,
                        android.R.layout.simple_spinner_item, goalNames);
                goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                goalSpinner.setAdapter(goalAdapter);

                //goalSpinner.setSelection(goalIndex);

                checkAllDataGathered();
            }
        });
    }

    private void initDietPreferenceSpinnerValues()
    {
        googleMySQLDataBase.getTable(DataTables.DietPreference, DietPreferenceData.class, new DataBase.MultipleDataCallback<DietPreferenceData>()
        {
            @Override
            public void onResponse(List<DietPreferenceData> dataList)
            {
                dietPreferenceDataList = dataList;

                List<String> dietPreferenceNames = new ArrayList<>();
                dietPreferenceNames.add("None");

                for (DietPreferenceData dietPreferenceData : dataList)
                {
                    dietPreferenceNames.add(dietPreferenceData.getDietPreferenceName());
                }

                ArrayAdapter<String> dietPreferenceAdapter = new ArrayAdapter<>(UserInformation.this,
                        android.R.layout.simple_spinner_item, dietPreferenceNames);
                dietPreferenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dietPreferenceSpinner.setAdapter(dietPreferenceAdapter);

                //dietPreferenceSpinner.setSelection(dietPreferenceIndex);

                checkAllDataGathered();
            }
        });
    }

    private void initAllergenValues()
    {
        googleMySQLDataBase.getTable(DataTables.Allergen, AllergenData.class, new DataBase.MultipleDataCallback<AllergenData>()
        {
            @Override
            public void onResponse(List<AllergenData> dataList)
            {
                allergenDataList = dataList;
                checkAllDataGathered();
            }
        });

        googleMySQLDataBase.getTable(DataTables.UserAllergy, UserAllergyData.class, new DataBase.MultipleDataCallback<UserAllergyData>()
        {
            @Override
            public void onResponse(List<UserAllergyData> dataList)
            {
                userAllergyDataList = dataList;
                checkAllDataGathered();
            }
        });
    }

    private void initUserInformation()
    {
        userPref = getSharedPreferences("User", MODE_PRIVATE);

        String userID = userPref.getString("UserID", "");

        if(!userID.equals(""))
        {
            if(userDataJSONHelper.doesFileExist(this))
            {
                UserData userData = userDataJSONHelper.readSingleDataJSON(this, UserData.class);

                Log.d("local", userData.toString());

                if(userData.getUserID().equals(userID))
                {
                    this.userData = userData;
                    initUserDataToUserInformation(userData);
                    return;
                }
            }

            googleMySQLDataBase.getById(DataTables.User, UserData.class, userID, new DataBase.SingleDataCallback<UserData>()
            {
                @Override
                public void onResponse(UserData data)
                {
                    userData = data;
                    initUserDataToUserInformation(data);
                }
            });
        }
    }

    private void initUserDataToUserInformation(UserData userData)
    {
        if(userData.getUsername() != null)
        {
            userName.setText(userData.getUsername());
        }

        Log.d("user", userData.toString());

        if(userData.getAge() != null)
        {
            age.setText(userData.getAge().toString());
        }

        if(userData.getHeight() != null)
        {
            height.setText(userData.getHeight().toString());
        }

        if(userData.getWeight() != null)
        {
            weight.setText(userData.getWeight().toString());
        }

        if(userData.getExtra() != null)
        {
            extraInformation.setText(userData.getExtra());
        }

        if (userData.getGender() != null)
        {
            int genderPosition = ((ArrayAdapter<String>) genderSpinner.getAdapter()).getPosition(userData.getGender().toString());
            genderSpinner.setSelection(genderPosition);
        }
        else
        {
            genderSpinner.setSelection(0);
        }

        if (userData.getGoalID() != null)
        {
            //goalIndex = userData.getGoalID()-1;
            goalSpinner.setSelection(userData.getGoalID()-1);
        }
        else
        {
            goalSpinner.setSelection(0);
        }

        if (userData.getActivityLevel() != null)
        {
            int activityLevelPosition = userData.getActivityLevel().ordinal()+1;
            activityLevelSpinner.setSelection(activityLevelPosition);
        }
        else
        {
            activityLevelSpinner.setSelection(0);
        }

        if (userData.getDietPreferenceID() != null)
        {
            //dietPreferenceIndex = userData.getDietPreferenceID()-1;
            dietPreferenceSpinner.setSelection(userData.getDietPreferenceID()-1);
        }
        else
        {
            dietPreferenceSpinner.setSelection(0);
        }

        StringBuilder allergenText = new StringBuilder();

        boolean firstAllergen = true;

        for (UserAllergyData userAllergyData : userAllergyDataList)
        {
            if(userAllergyData.getUserID().equals(userData.getUserID()))
            {
                if(!firstAllergen)
                {
                    allergenText.append(",");
                }

                allergenText.append(allergenDataList.get(userAllergyData.getAllergenID() - 1).getAllergenName());
                firstAllergen = false;
            }
        }

        allergen.setText(allergenText.toString());

        if(userData.getRegionID() != null)
        {
            String countryName = regionDataList.get(userData.getRegionID()-1).getCountry();
            String stateName = regionDataList.get(userData.getRegionID()-1).getCity();

            for(int i = 0; i < countryList.size(); i++)
            {
                Country country = countryList.get(i);

                if(country.getName().equals(countryName))
                {
                    externalStateSelection = true;
                    countrySpinner.setSelection(i+1);

                    List<String> stateNames = country.getStates();

                    Log.d("state", stateNames.toString());

                    for (int j = 0; j < stateNames.size(); j++)
                    {
                        if(stateNames.get(j).equals(stateName))
                        {
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this,
                                    android.R.layout.simple_spinner_item, stateNames);

                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            stateSpinner.setAdapter(stateAdapter);

                            stateSpinner.setSelection(j);
                            return;
                        }
                    }
                }
            }
        }
        else
        {
            countrySpinner.setSelection(0);
            stateSpinner.setSelection(0);
        }
    }

    private void updateStateSpinner(int countryIndex)
    {
        if (countryIndex == 0)
        {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("None");

            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptyList);
            emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(emptyAdapter);
        }
        else
        {
            List<String> stateNames = countryList.get(countryIndex - 1).getStateNames();
            ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stateNames);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(stateAdapter);
        }
    }

    private List<Country> loadCountriesFromJson()
    {
        List<Country> countries = new ArrayList<>();

        try
        {
            InputStream inputStream = getAssets().open("countries_states.json");

            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country(jsonObject);
                countries.add(country);
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return countries;
    }

    private void setCountrySpinnerListener()
    {
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(!externalStateSelection)
                {
                    updateStateSpinner(position);
                }

                externalStateSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                stateSpinner.setAdapter(null);
            }
        });
    }

    private void setSaveButtonListener()
    {
        saveUserInformation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveUserData();

                Intent intent;

                String from = getIntent().getStringExtra("from");

                if (from != null && from.equals("MainActivity"))
                {
                    finish();
                }
                else
                {
                    intent = new Intent(UserInformation.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void saveUserData()
    {
        String name = userName.getText().toString();
        String userAge = age.getText().toString();
        String userHeight = height.getText().toString();
        String userWeight = weight.getText().toString();
        String extra = extraInformation.getText().toString();

        userData.setUsername(name);
        userData.setAge(Integer.valueOf(userAge));
        userData.setHeight(Integer.valueOf(userHeight));
        userData.setWeight(Integer.valueOf(userWeight));
        userData.setExtra(extra);


        int genderIndex = genderSpinner.getSelectedItemPosition();
        int goalID = goalSpinner.getSelectedItemPosition();
        int activityLevelIndex = activityLevelSpinner.getSelectedItemPosition();
        int dietPreferenceID = dietPreferenceSpinner.getSelectedItemPosition();

        String country = countrySpinner.getSelectedItem().toString();
        String state = stateSpinner.getSelectedItem().toString();


        if(genderIndex == 0)
        {
            userData.setGender(null);
        }
        else
        {
            userData.setGender(UserData.Gender.values()[genderIndex-1]);
        }

        if(goalID == 0)
        {
            userData.setGoalID(null);
        }
        else
        {
            userData.setGoalID(goalID);
        }

        if(activityLevelIndex == 0)
        {
            userData.setActivityLevel(null);
        }
        else
        {
            userData.setActivityLevel(UserData.ActivityLevel.values()[activityLevelIndex-1]);
        }

        if(dietPreferenceID == 0)
        {
            userData.setDietPreferenceID(null);
        }
        else
        {
            userData.setDietPreferenceID(dietPreferenceID);
        }

        int countryIndex = countrySpinner.getSelectedItemPosition();

        if(countryIndex == 0)
        {
            userData.setRegionID(null);
        }
        else
        {
            boolean notFounded = true;

            for (RegionData regionData : regionDataList)
            {
                if(regionData.getCountry().equals(country) && regionData.getCity().equals(state))
                {
                    notFounded = false;
                    userData.setRegionID(regionData.getRegionID());
                    break;
                }
            }

            if(notFounded)
            {
                RegionData regionData = new RegionData(regionDataList.size()+1, countryList.get(countrySpinner.getSelectedItemPosition()+1).getName(),
                        countryList.get(countrySpinner.getSelectedItemPosition()+1).getStates().get(stateSpinner.getSelectedItemPosition()+1));

                googleMySQLDataBase.insert(DataTables.Region, RegionData.class, regionData);

                userData.setRegionID(regionDataList.size()+1);
            }
        }

        String text = allergen.getText().toString();
        String[] allergenNames = text.split(",");

        int sizeCounter = 0;

        for (String allergenName : allergenNames)
        {
            int allergenID = -1;
            boolean allergenNameFounded = false;

            for (AllergenData allergenData : allergenDataList)
            {
                if(allergenData.getAllergenName().equals(allergenName))
                {
                    allergenID = allergenData.getAllergenID();
                    allergenNameFounded = true;
                    break;
                }
            }

            if(!allergenNameFounded)
            {
                allergenID = allergenDataList.size() + 1 + sizeCounter++;
                googleMySQLDataBase.insert(DataTables.Allergen, AllergenData.class,
                        new AllergenData(allergenID, allergenName));
            }

            boolean userAllergyFounded = false;
            boolean userAllergyDeleted = true;

            for (UserAllergyData userAllergyData : userAllergyDataList)
            {
                if(userAllergyData.getUserID().equals(userData.getUserID())
                        && userAllergyData.getAllergenID() == allergenID)
                {
                    userAllergyFounded = true;
                    //break;
                }

                if(userAllergyData.getUserID().equals(userData.getUserID())
                        && allergenName.equals(allergenDataList.get(
                                userAllergyData.getAllergenID()-1).getAllergenName()))
                {
                    userAllergyDeleted = false;
                }
            }

            if(!userAllergyFounded)
            {
                googleMySQLDataBase.insert(DataTables.UserAllergy, UserAllergyData.class,
                        new UserAllergyData(userData.getUserID(), allergenID));
            }

            if(userAllergyDeleted)
            {
                // delete
                // googleMySQLDataBase.delete(DataTables.UserAllergy, UserAllergyData.class, );
            }

        }

        googleMySQLDataBase.update(DataTables.User, UserData.class, userData.getUserID(), userData);
        userDataJSONHelper.writeJSON(this, UserData.class, userData);
    }


    private void setLogOutButtonListener()
    {
        logOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserInformation.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}