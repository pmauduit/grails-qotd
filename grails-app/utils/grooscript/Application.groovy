package grooscript

import org.grooscript.asts.GsNative

class Application {
    @GsNative
    getAngular() {
        /* return window.angular; */
    }
    def init() {
      def app = angular.module('myApp', ['myAppControllers', 'ngRoute'])

      app.config { $routeProvider, $locationProvider ->
          $routeProvider.when('/', [
                  templateUrl: 'html/background.html'
              ]).otherwise({
                  redirectTo: '/'
              })
      }
  }
}
