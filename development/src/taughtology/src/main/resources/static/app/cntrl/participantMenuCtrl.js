'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('participantMenuCtrl', ['$scope', 'userService', function ($scope, userService) {

        $scope.user = {};

        userService.contextUser().then(function(user){
            $scope.user = user;
        });

    }]);
})();