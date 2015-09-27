'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('profileHeaderCtrl', ['$scope', 'User', 'Broad', function ($scope, User, Broad) {
        $scope.user = User.get({id:'me'});
        $scope.platformInfo = Broad.get({id: 'platform-info'});
        $scope.course = Broad.get({id: 'course-info'});

        $scope.course.$promise.then(function(course){
            Broad.update({id:"course-info"}, course);
        });

    }]);
})();