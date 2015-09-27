'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('ProfileCtrl', ['$scope', '$location', 'broadcontext', function ($scope, broadcontext, $location) {

        $scope.profile = {
            fullName: "Jhon Doe",
            picture: "http://api.randomuser.me/portraits/thumb/men/58.jpg",
            points: 5443,
            rating: 4,
            lastUpdated: new Date()
        };

    }]);
})();