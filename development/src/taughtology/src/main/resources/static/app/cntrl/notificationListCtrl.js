'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('NotificationListCtrl', ['$scope', '$moment', 'currentUser', 'Notification', function ($scope, $moment, currentUser, Notification) {

        Notification.query({to:currentUser.id}).$promise.then(function(notifications){
            $scope.notifications = notifications;
        });

        var fromNow = function(time){
            return $moment(time).fromNow();
        };

        var read = function(notification){
            if (!!notification.seen) {
                notification.seen = true;
                notification.$save();
            }
        };


        $scope.notifications = [];
        $scope.read = read;
        $scope.fromNow = fromNow;

    }]);
})();