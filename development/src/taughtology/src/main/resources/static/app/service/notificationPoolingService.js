'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('notificationPoolingService',['$timeout', 'Notification', 'userService', function ($timeout, Notification, userService) {
        var _notifications = {data:[], updated:0};

        var TEN_SECONDS = 10000;

        userService.contextUser().then(function(user){
            var poller = function() {
                Notification.query({to:user.id, seen:false}).$promise.then(function(notifications) {
                    _notifications.data = notifications;
                    _notifications.updated = Date.now();
                    $timeout(poller, 10000, TEN_SECONDS);
                });
            };
            poller();
        });

        return _notifications;
    }]);
})();