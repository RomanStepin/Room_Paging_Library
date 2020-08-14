package com.example.myapplication;

import java.util.Random;

public class PersonGenerator
{
    static Random random = new Random(50);

    static   private String getName()
    {
        String name = "";
        int length = random.nextInt(10) + 2; //97 122
        random = new Random();
        for (int i = 0; i < length; i++)
        {
            name = name + (char)(random.nextInt(25) + 97);
        }

        return name;
    }

    public Person getPerson()
    {
        return new Person(getName(),getName());
    }
}
