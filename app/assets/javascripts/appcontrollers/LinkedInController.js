/**
 * Created by skunnumkal on 7/18/13.
 */
function LinkedInController($scope,userService){
    console.log("LinkedInController..loading");
    $scope.authenticated = false;
    $scope.userName = "Someone";
    $scope.bUser = aUser || undefined;
    $scope.setAuthenticatedUser = function(liUser){
        $scope.authenticated = true;

        userService.currentUser = liUser;
        $scope.userName = userService.currentUser.name;
        if(liUser.imageUrl == undefined){
            userService.currentUser.imageUrl =  '/assets/images/personal_user_128.png';
        }
        console.log("Setting basic user object after Linkedin Auth",userService.currentUser);
        var someResultPromise = userService.registerOrLogin(liUser);
           someResultPromise.then(function(result){
               console.log("Result of 2nd promise",result," So user exists =",result);
               if(result){
                   $scope.isNew = false;
               }

           },function(errResponse){
               console.error("Didn't create or check user",errResponse);
           });


    };

    if($scope.bUser != undefined && $scope.bUser != null){
        console.log("bUser is not null");
        $scope.authenticated = true;
        userService.currentUser = $scope.bUser;
    }



}

LinkedInController.$inject = ['$scope','userService'];
