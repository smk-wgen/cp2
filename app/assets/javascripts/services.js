/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
var servicesModule = angular.module('appServices', []);
servicesModule.factory('userService',function($http){

      var aUserService = {
         currentUser : '',
         getUser : function(id){
             console.log("Call the backend for the user");
         },
         getUserCommutes : function(id) {
             console.log("Get users commutes");
         },
         getUserAddresses : function(id){
             console.log("Get user addresses");
         },
         isRegistered  : function(id){
             var userPromise = $http.get('/checkExists/'+id);
             var result = null;
             var anotherPromise = userPromise.then(function(response){
                 var responseData = response.data;
                 console.log("Response from backEnd for user",responseData);
                 if(responseData.isNew === false){
                     aUserService.currentUser = responseData.user;
                     console.log("Setting authenticated (currentUser) as",aUserService.currentUser);
                     console.log("User exists in our db..Now forward to dashboard");
                     result = true;
                 }
                 else{
                     console.log("New User..");
                     result = false;
                 }

                 return result;


             });

             return anotherPromise;

         }
      };
    return aUserService;
});
servicesModule.factory('addressService',function($http){

    return {
             getUserAddresses: function(id){
                 return $http.get('/addresses/'+id);
             },
             addUserAddress : function(address){
                 console.log("Create address payload",address);
                 return $http.post("/address", address);
             }
    };
});
servicesModule.factory('commuteService',function($http){
     return{
         getUserCommutes : function(id){
             return $http.get('/usercommutes/'+id);
         }
     };
});


