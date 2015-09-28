'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('profileHeaderCtrl', ['$scope', 'User', 'Broad', 'ConquestCount', function ($scope, User, Broad, ConquestCount) {
        $scope.user = User.get({id:'me'});
        $scope.platformInfo = Broad.get({id: 'platform-info'});
        $scope.course = Broad.get({id: 'course-info'});

        $scope.course.$promise.then(function(course){
            Broad.update({id:"course-info"}, course);
        });

        $scope.conquestsCount = {count:0};

        $scope.user.$promise.then(function(user){
            $scope.conquestsCount = ConquestCount.get({id: user.id});
        });

    }]);
})();