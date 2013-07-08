/**
 * Created by skunnumkal on 6/30/13.
 */
'use strict';
angular.module('appServices', []).service('addressService', function () {
    var myAddresses = [];
    function getAddresses () {
        myAddresses = [
            {
                "label": "Home",
                "line1": "10871ststreetGuttenberg",
                "line2": "",
                "city": "Guttenberg",
                "state": "NJ",
                "zip": 19094
            },
            {
                "label": "Room2",
                "line1": "53HamiltonAve",
                "line2": "",
                "city": "StatenIsland",
                "state": "NY",
                "zip": 9394
            },
            {
                "label": "Work",
                "line1": "55WashStreet",
                "line2": "824",
                "city": "NY",
                "state": "NY",
                "zip": 1094
            }
        ];
        return myAddresses;
    };
    function addAddress(address){
        myAddresses.push(address);
    }
    return { myAddresses : getAddresses(),
             getUserAddresses: getAddresses,
             addUserAddress : addAddress};
});
