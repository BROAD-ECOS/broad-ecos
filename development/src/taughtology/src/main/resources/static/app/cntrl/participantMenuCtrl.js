'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('userMenuCtrl', ['$scope', 'User', function ($scope, User) {

        User.me().$promise.then(function(){
            console.log(me);
        })

    }]);
})();