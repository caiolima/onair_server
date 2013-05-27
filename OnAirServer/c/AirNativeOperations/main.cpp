//
//  main.cpp
//  AirNativeOperations
//
//  Created by Caio Lima on 26/10/12.
//  Copyright (c) 2012 Five Startup. All rights reserved.
//

#include <JavaVM/jni.h>
#include <Security/AuthorizationTags.h>
#include <Security/Authorization.h>
#include <string>
#include <stdio.h>
#include "com_five_onair_server_utils_FileUtils.h"

jboolean copyDirectory(char *from,char *dest);
jboolean deleteDirectory(char *path);
jboolean executeCommandWithRoot(char *tool_path,char **arguments);

JNIEXPORT jboolean JNICALL Java_com_five_onair_server_utils_FileUtils_copyDirectoryLikeRoot
(JNIEnv *env, jclass m_class, jstring _from, jstring _dest){
    //
    //    char *from=NULL,*dest=NULL;
    //
    //    strcpy(from, env->GetStringUTFChars(_from, NULL));
    //    strcpy(dest, env->GetStringUTFChars(_dest, NULL));
    //
    //    copyDirectory(from, dest);
    char * from,*dest;
    from = const_cast<char *>(env->GetStringUTFChars(_from, NULL));
    dest = const_cast<char *>(env->GetStringUTFChars(_dest, NULL));
    
    return copyDirectory(from, dest);
    
}

JNIEXPORT jboolean JNICALL Java_com_five_onair_server_utils_FileUtils_deleteDirectoryWithRoot
(JNIEnv *env, jclass _class, jstring _path){
    char *path;
    
    path=const_cast<char *>(env->GetStringUTFChars(_path, NULL));
    
    return deleteDirectory(path);
    
    
}


//private operations
jboolean executeCommandWithRoot(char *tool_path,char **arguments){
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
       
        FILE *myCommunicationsPipe = NULL;
        myFlags = kAuthorizationFlagDefaults;                          // 8
        
        myStatus = AuthorizationExecuteWithPrivileges(myAuthorizationRef, tool_path, myFlags, arguments,&myCommunicationsPipe);
        
        AuthorizationFree (myAuthorizationRef, kAuthorizationFlagDefaults);
        if (myStatus == errAuthorizationSuccess){
            return JNI_TRUE;
        }
        return JNI_FALSE;
        
    }
    
    AuthorizationFree (myAuthorizationRef, kAuthorizationFlagDefaults);
    return JNI_FALSE;
}

jboolean copyDirectory(char *from,char *dest){
    
    char myToolPath[] = "/bin/cp";
    char *myArguments[] = { "-r", from, dest, NULL };
    
    return executeCommandWithRoot(myToolPath, myArguments);
    
}

jboolean deleteDirectory(char *path){
    
    char myToolPath[] = "/bin/rm";
    char *myArguments[] = { "-f", "-r", path, NULL };
    
    return executeCommandWithRoot(myToolPath, myArguments);
    
}
