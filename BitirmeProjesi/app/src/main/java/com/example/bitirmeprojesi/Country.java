package com.example.bitirmeprojesi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Country
{
    private String name;
    private List<String> states;

    public Country(JSONObject jsonObject)
    {
        try
        {
            this.name = jsonObject.getString("name");
            this.states = new ArrayList<>();
            JSONArray statesArray = jsonObject.getJSONArray("states");

            for (int i = 0; i < statesArray.length(); i++)
            {
                JSONObject stateObject = statesArray.getJSONObject(i);
                String stateName = stateObject.getString("name");
                this.states.add(stateName);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getName()
    {
        return name;
    }

    public List<String> getStates()
    {
        return states;
    }

    public List<String> getStateNames()
    {
        List<String> stateNames = new ArrayList<>();
        for (String state : states)
        {
            stateNames.add(state);
        }
        return stateNames;
    }
}

