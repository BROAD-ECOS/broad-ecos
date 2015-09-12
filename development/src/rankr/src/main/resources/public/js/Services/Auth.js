'use strict';

angular.module('lopl')
.factory('Auth', ['$http', function ($http) {
    return {
        login: function(credentials){
            // Do the login things
            return;
        },
        logout: function(){
            // Do the logout things
            return;
        },
        check: function(){
            // Return true or false if the user is authenticated
            return true;
        },
        user: function(){
            // Return the user object if is authenticated
            return {};
        }
    };
}])
