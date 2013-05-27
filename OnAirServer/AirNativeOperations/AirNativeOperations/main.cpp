//
//  main.cpp
//  AirNativeOperations
//
//  Created by Caio Lima on 26/10/12.
//  Copyright (c) 2012 Five Startup. All rights reserved.
//

#include <jni.h>
#include <AuthorizationTags.h>
#include <Authorization.h>
#include <string>
#include "com_five_onair_server_utils_FileUtils.h"

JNIEXPORT jboolean JNICALL Java_com_five_onair_server_utils_FileUtils_copyDirectoryLikeRoot
(JNIEnv *env, jclass m_class, jstring _from, jstring _dest){

    OSStatus myStatus;
    AuthorizationFlags myFlags = kAuthorizationFlagDefaults;              // 1
    AuthorizationRef myAuthorizationRef;                                  // 2
    
    myStatus = AuthorizationCreate(NULL, kAuthorizationEmptyEnvironment,  // 3
                                   
                                   myFlags, &myAuthorizationRef);
    
    if (myStatus != errAuthorizationSuccess)
        return myStatus;
    
    AuthorizationItem myItems = {kAuthorizationRightExecute, 0,NULL, 0};
    
    AuthorizationRights myRights = {1, &myItems};                 //
    
    myFlags = kAuthorizationFlagDefaults |                         // 6
    kAuthorizationFlagInteractionAllowed |
    kAuthorizationFlagPreAuthorize |
    kAuthorizationFlagExtendRights;
    
    myStatus = AuthorizationCopyRights (myAuthorizationRef, &myRights, NULL, myFlags, NULL );
    
    if (myStatus == errAuthorizationSuccess){
       
        char *from = NULL,*dest = NULL,*flag=NULL;
    
        strcpy(from, env->GetStringUTFChars(_from, NULL));
        strcpy(dest, env->GetStringUTFChars(_dest, NULL));
        strcpy(flag, "-r");
        
        char myToolPath[] = "/bin/cp";
        char *myArguments[] = { flag, from, dest, NULL };
        FILE *myCommunicationsPipe = NULL;
        
        
        
        
        myFlags = kAuthorizationFlagDefaults;                          // 8
        
        myStatus = AuthorizationExecuteWithPrivileges(myAuthorizationRef, myToolPath, myFlags, myArguments,&myCommunicationsPipe);
        
        
        if (myStatus == errAuthorizationSuccess){
            return true;
        }
        return false;
        
    }
    
    AuthorizationFree (myAuthorizationRef, kAuthorizationFlagDefaults); // 10
    
}
