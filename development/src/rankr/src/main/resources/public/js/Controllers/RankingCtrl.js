"use strict";
angular.module('lopl')
    .controller('RankingCtrl', ['$scope', 'Profile', 'moment', '$rootScope', function ($scope, Profile, moment, $rootScope) {

               $scope.highestScore = 0;


               var getStarts = function(score) {
                    var starValue = $scope.highestScore / 5;
                    return Math.ceil(score/starValue);
               };

               $scope.global = [];

               $scope.region = [];

               $scope.recent = [];


               Profile.query({'limit':10,'order':'score', "direction":"DESC"},function(profiles){

                    if (profiles.length > 0) {

                        $scope.highestScore = profiles[0].score;

                        angular.forEach(profiles, function(profile){
                            $scope.global.push({
                               id: profile.id,
                                name: profile.participant.name || 'Anônimo',
                                score: profile.score || 0,
                                points: profile.conquests.POINTS || {total:0},
                                medals: profile.conquests.MEDALS || {total:0},
                                stars: getStarts(profile.score),
                                updatedAt: moment(profile.updatedAt).fromNow(),
                                createdAt: new Date(profile.createdAt).toString(),
                                organization: profile.participant.organization || {bizId: "-", name:"Desconhecido"}
                            });
                        });
                    }

               Profile.query({'limit':10,'order':'score', "organizationId":$rootScope.participant.organization.bizId, "direction":"DESC"},function(profiles){
                   if (profiles.length > 0) {
                       angular.forEach(profiles, function(profile){
                           $scope.region.push({
                               id: profile.id,
                               name: profile.participant.name || 'Anônimo',
                               score: profile.score || 0,
                               points: profile.conquests.POINTS || {total:0},
                               medals: profile.conquests.MEDALS || {total:0},
                               stars: getStarts(profile.score),
                               updatedAt: moment(profile.updatedAt).fromNow(),
                               createdAt: new Date(profile.createdAt).toString(),
                               organization: profile.participant.organization || {id: "-", name:"Desconhecido"}
                           });
                       });
                   }

               })


               Profile.query({'limit':10,'order':'updatedAt', "direction":"DESC"},function(profiles){
                  if (profiles.length > 0) {
                      angular.forEach(profiles, function(profile){
                          $scope.recent.push({
                               id: profile.id,
                              name: profile.participant.name || 'Anônimo',
                              score: profile.score || 0,
                              points: profile.conquests.POINTS || {total:0},
                              medals: profile.conquests.MEDALS || {total:0},
                              stars: getStarts(profile.score),
                              updatedAt: moment(profile.updatedAt).fromNow(),
                              createdAt: new Date(profile.createdAt).toString(),
                              organization: profile.participant.organization || {id: "-", name:"Desconhecido"}
                          });
                      });
                  }

              })

               })


    }])