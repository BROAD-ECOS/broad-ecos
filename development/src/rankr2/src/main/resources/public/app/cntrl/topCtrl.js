'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('TopCtrl', ['$scope', 'Ranking',  '$moment', 'ConquestCount', function ($scope, Ranking,  $moment, ConquestCount) {
        var countConquests = function(user){
            user.conquestsCount = ConquestCount.get({id: user.id});
        };

        $scope.tops = Ranking.get({id: 'top', 'page':0, 'pageSize':100});
        $scope.fromNow = $moment.fromNow;


        $scope.tops.$promise.then(function(tops){
            angular.forEach(tops.content, function (top){
                countConquests(top);
            });
        });

    }]);
})();