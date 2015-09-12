"use strict";
angular.module('lopl')
    .controller('ProfileCtrl', ['$routeParams', '$scope', 'Profile', 'Conquest', 'moment', '$rootScope', function ($routeParams, $scope, Profile, Conquest, moment, $rootScope) {

           $scope.highestScore = 0;
           $scope.stars = 0;
           $scope.profile = {};
           $scope.createdAt = '';
           $scope.updatedAt = '';
           $scope.points =  {total:0};
           $scope.medals = {total:0};
           $scope.conquests = [];

           var getStarts = function(currentScore, greaterScore) {
                var starValue = greaterScore / 5;
                return Math.ceil(currentScore/starValue);
           };

           Profile.get({id:$routeParams.id}, function(profile){

                $scope.profile = profile;
                $scope.updatedAt = moment(profile.updatedAt).fromNow();
                $scope.createdAt = moment(profile.createdAt).fromNow();
                $scope.points = profile.conquests.POINTS || $scope.points;
                $scope.medals = profile.conquests.MEDALS || $scope.medals;

                Profile.query({'limit':1,'order':'score', "direction":"DESC"},function(profiles){
                    var greaterScore =profiles[0];
                    $scope.stars = getStarts(profile.score, greaterScore.score);
                });


                Conquest.query({profileId: profile.id}, function(conquests){
                    var transformedConquests = [];
                    angular.forEach(conquests, function(conquest) {
                        conquest.updatedAtFromNow = moment(conquest.updatedAt).fromNow()
                        transformedConquests.push(conquest);
                    });
                    $scope.conquests = transformedConquests;
                });

           });





    }])