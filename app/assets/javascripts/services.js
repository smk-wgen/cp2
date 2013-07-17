/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
var servicesModule = angular.module('appServices', []);
servicesModule.factory('userService',function($http){
      return {
         currentUser : 1,
         getUser : function(id){
             console.log("Call the backend for the user");
         },
         getUserCommutes : function(id) {
             console.log("Get users commutes");
         },
         getUserAddresses : function(id){
             console.log("Get user addresses");
         }
      };
});
servicesModule.factory('addressService',function($http){
    var myAddresses = [];
    function getAddresses (id) {
        //return $http.(SapphireConfig.csUrlBuilder('/action/interact/enactedComponent/'+cardStack.key+'/markActive'),{})
        return $http.get('/addresses/'+id);
//        return $http.get('/addresses/'+id).success(function(data){
//            console.log(data);
//            myAddresses = data;
//        }).error(function(data){console.log(data);});
//        console.log("Returning myaddreses",myAddresses);
//        return myAddresses;
    };
    function addAddress(address){
        myAddresses.push(address);
    }
    return {
             getUserAddresses: getAddresses,
             addUserAddress : addAddress};
});
servicesModule.factory('commuteService',function($http){
     return{
         getUserCommutes : function(id){
             return $http.get('/usercommutes/'+id);
         }
     };
});