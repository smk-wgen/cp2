/**
 * Created by skunnumkal on 7/18/13.
 */
function LinkedInController($scope,userService){
    $scope.authenticated = false;
    $scope.userName = "Someone";
    $scope.setAuthenticatedUser = function(liUser){
        $scope.authenticated = true;

        userService.currentUser = liUser;
        $scope.userName = userService.currentUser.name;
        if(liUser.imageUrl == undefined){
            userService.currentUser.imageUrl =  '/assets/images/personal_user_128.png';
        }
        console.log("Setting basic user object after Linkedin Auth",userService.currentUser);
        var someResultPromise = userService.isRegistered(liUser.linkedInMemberId);
           someResultPromise.then(function(result){
               console.log("Result of 2nd promise",result," So user exists =",result);
               if(result){
                   $scope.isNew = false;
               }

           },function(errResponse){
               console.error("Didn't create or check user",errResponse);
           });


    };



}

LinkedInController.$inject = ['$scope','userService'];
