package com.alfa1.opdracht3;

import com.alfa1.opdracht2.MyOutput;

import java.util.UUID;

interface MyRepository {
    MyOutput get(UUID id);
    MyOutput get(String name); // Method used by new tests
}