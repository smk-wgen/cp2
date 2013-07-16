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
        $http.get('/addresses/'+id).success(function(data){
            console.log(data);
            myAddresses = data;
        }).error(function(data){console.log(data);});
        return myAddresses;
    };
    function addAddress(address){
        myAddresses.push(address);
    }
    return {
             getUserAddresses: getAddresses,
             addUserAddress : addAddress};
});
//angular.module('appServices', []).service('addressService', function () {
//    var myAddresses = [];
//    function getAddresses () {
//        myAddresses = [
//            {
//                "label": "Home",
//                "line1": "10871ststreetGuttenberg",
//                "line2": "",
//                "city": "Guttenberg",
//                "state": "NJ",
//                "zip": 19094
//            },
//            {
//                "label": "Room2",
//                "line1": "53HamiltonAve",
//                "line2": "",
//                "city": "StatenIsland",
//                "state": "NY",
//                "zip": 9394
//            },
//            {
//                "label": "Work",
//                "line1": "55WashStreet",
//                "line2": "824",
//                "city": "NY",
//                "state": "NY",
//                "zip": 1094
//            }
//        ];
//        return myAddresses;
//    };
//    function addAddress(address){
//        myAddresses.push(address);
//    }
//    return { myAddresses : getAddresses(),
//             getUserAddresses: getAddresses,
//             addUserAddress : addAddress};
//});
