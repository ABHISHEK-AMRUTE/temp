package com.amrute_studio.mediscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class dataClass {
    String name,nameMemberOne,nameMemberTwo;
    String phone,phoneMemberOne,phoneSecondmember;
    String gender,bloodGroup,uid;
    float age,height,weight;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void setData(String name, String nameMemberOne, String nameSecondName, String phone, String phoneMemberOne, String phoneSecondName, String gender, String bloodGroup, String uid, float age, float height, float weight) {
        this.name = name;
        this.nameMemberOne = nameMemberOne;
        this.nameMemberTwo = nameSecondName;
        this.phone = phone;
        this.phoneMemberOne = phoneMemberOne;
        this.phoneSecondmember = phoneSecondName;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.uid = uid;

        setName(name);
        setNameMemberOne(nameMemberOne);
        setNameSecondName(nameMemberTwo);
        setPhone(phone);
        setPhoneMemberOne(phoneMemberOne);
        setPhoneSecondName(phoneSecondName);
        setGender(gender);
        setBloodGroup(bloodGroup);
        setAge(age);
        setHeight(height);
        setWeight(weight);
        setUid(uid);


    }

    public dataClass(Context context)
    {
        sharedPreferences = context.getSharedPreferences("userDataFile", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getData();
    }


    public void getData() {
        this.name = sharedPreferences.getString("name","Guest");
        this.nameMemberOne = sharedPreferences.getString("nameMemberOne","0");
        this.nameMemberTwo = sharedPreferences.getString("nameMemberTwo","Guest");
        this.phone = sharedPreferences.getString("phone","0");
        this.phoneMemberOne = sharedPreferences.getString("phoneMemberOne","0");
        this.phoneSecondmember = sharedPreferences.getString("phoneSecondmember","0");
        this.gender = sharedPreferences.getString("gender","Male");
        this.bloodGroup = sharedPreferences.getString("bloodGroup","O+");
        this.age = sharedPreferences.getFloat("age",18F);
        this.height = sharedPreferences.getFloat("height",130F);
        this.weight = sharedPreferences.getFloat("weight",55F);
        this.uid = sharedPreferences.getString("uid","no_user");
        }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        editor.putString("uid", uid);
        editor.commit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        editor.putString("name", name);
        editor.commit();
    }

    public String getNameMemberOne() {
        return nameMemberOne;
    }

    public void setNameMemberOne(String nameMemberOne) {
        this.nameMemberOne = nameMemberOne;
        editor.putString("nameMemberOne", nameMemberOne);
        editor.commit();
    }

    public String getNameSecondName() {
        return nameMemberTwo;
    }

    public void setNameSecondName(String nameSecondName) {
        this.nameMemberTwo = nameSecondName;
        editor.putString("nameMemberTwo", nameMemberTwo);
        editor.commit();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        editor.putString("phone", phone);
        editor.commit();
    }

    public String getPhoneMemberOne() {
        return phoneMemberOne;

    }

    public void setPhoneMemberOne(String phoneMemberOne) {
        this.phoneMemberOne = phoneMemberOne;
        editor.putString("phoneMemberOne", phoneMemberOne);
        editor.commit();
    }

    public String getPhoneSecondName() {
        return phoneSecondmember;
    }

    public void setPhoneSecondName(String phoneSecondName) {
        this.phoneSecondmember = phoneSecondName;
        editor.putString("phoneSecondmember", phoneSecondmember);
        editor.commit();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        editor.putString("gender", gender);
        editor.commit();
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        editor.putString("gender", gender);
        editor.commit();
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
        editor.putFloat("age", age);
        editor.commit();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        editor.putFloat("height", height);
        editor.commit();
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
        editor.putFloat("weight", weight);
        editor.commit();
    }
}
