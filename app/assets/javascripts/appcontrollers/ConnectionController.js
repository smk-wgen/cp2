/**
 * Created by skunnumkal on 8/29/13.
 */
function ConnectionController($scope,$http){

       $scope.addConnection = function(){


           console.log("Verifying",fromUserId,toUserId,commuteId);
           $http.post('/connect',{fromUserId : fromUserId,toUserId : toUserId, commuteId:commuteId}).
               success(function(response){
                   alert("Notified the user");
               }).
               error(function(errResponse){
                   console.error(errResponse);
               });

       }

}
ConnectionController.$inject = ['$scope','$http'];