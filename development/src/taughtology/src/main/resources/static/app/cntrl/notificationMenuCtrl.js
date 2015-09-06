'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('NotificationMenuCtrl', ['$scope', 'notificationPoolingService',  function ($scope, notificationPoolingService) {

        $scope.notifications = notificationPoolingService;
        

    }]);
})();