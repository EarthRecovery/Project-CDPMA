package com.cdpma.common.pojo.enums;

public class UserLifeCycle {

    // User lifecycle stages constants
    public static final String NEW_USER = "NewUser";           // Newly registered or newly purchased user
    public static final String ACTIVE_USER = "ActiveUser";     // Recently active user
    public static final String DORMANT_USER = "DormantUser";   // User inactive for a while but not lost yet
    public static final String CHURNED_USER = "ChurnedUser";    // User who has stopped using the service for a long time
}
