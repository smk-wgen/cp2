/**
 * Created by skunnumkal on 8/29/13.
 */
function ConnectionController($scope,$http){
       $scope.addConnection = function(fromId,toId,commuteId){
           console.log("On click..");
           $http.post('/connect',{fromUserId : fromId,toUserId : toId, commuteId:commuteId}).
               success(function(response){
                   alert("Notified the user");
               }).
               error(function(errResponse){
                   console.error(errResponse);
               });

       }

}
ConnectionController.$inject = ['$scope','$http'];