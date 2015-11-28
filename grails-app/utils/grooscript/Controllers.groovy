package grooscript

import org.grooscript.asts.GsNative

class Controllers { 
    @GsNative
    getAngular() {
        /* return window.angular; */
    }
    def init() {
        def controllers = angular.module('myAppControllers', ['myAppServices'])
        controllers.controller('BackgroundJobCtrl', { $scope, $interval, $http, BackgroundJobService ->
            $interval({
                $scope.backgroundJobs = BackgroundJobService.get()
            }, 5000)

            $scope.refresh = {
                $scope.backgroundJobs = BackgroundJobService.get()
            }
            
            $scope.start = { job ->
                $http.get("/backgroundJobs/start/${job.identifier}").success({ data ->
                    $scope.refresh()
                })
            }

            $scope.remove = { job ->
                $http.get("/backgroundJobs/remove/${job.identifier}").success({ data ->
                    $scope.refresh()
                })
            }
            $scope.create = {
                $http.get("/backgroundJobs/create").success({ data ->
                    $scope.refresh()
                })
            }
            $scope.refresh()
        })
    }
    
}