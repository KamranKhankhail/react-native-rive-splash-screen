import Foundation
import React
import RiveRuntime

@objc(RiveSplashScreen)
public class RiveSplashScreen: NSObject {
    @objc public static let staticInstance: RiveSplashScreen = RiveSplashScreen()

    public static var waiting: Bool = true;
    public static var addedJsLoadErrorObserver: Bool = false;
    public static var loadingView: UIView? = nil;
    public static var riveViewModel: RiveViewModel? = nil;

    // number of seconds since 1970
    public static var splashScreenDisplayedAt: Double = Date().timeIntervalSince1970;

    // this saves the name of the splash image that is displayed during current run of the app.
    public static var nameOfSplashImageDisplayed: String = "";

    func methodQueue() -> DispatchQueue {
        return DispatchQueue.main
    }

    @objc(show)
    public func show() {
        if !RiveSplashScreen.addedJsLoadErrorObserver {
            NotificationCenter.default.addObserver(self, selector: Selector(("jsLoadError:")), name: NSNotification.Name.RCTJavaScriptDidFailToLoad, object: nil)
            RiveSplashScreen.addedJsLoadErrorObserver = true
        }

        while RiveSplashScreen.waiting {
            let later = Date(timeIntervalSinceNow: 0.1)
            RunLoop.main.run(until: later)
        }
    }

    //  Converted to Swift 5.7 by Swiftify v5.7.25750 - https://swiftify.com/
    @objc(showSplash:inRootView:withArtboard:withRiveFile:withStateMachine:)
    public func showSplash(_ splashScreen: String?, inRootView rootView: UIView?, withArtboard artboardName: String? = nil, withRiveFile fileName: String? = nil, withStateMachine stateMachine: String?) {
        if (RiveSplashScreen.loadingView == nil) {
            let vc = UIStoryboard(name: "SplashScreen", bundle: nil).instantiateViewController(withIdentifier: "SplashViewController")
            var frame = rootView?.frame
            frame?.origin = CGPoint(x: 0, y: 0)

            // if rive fileName is present, use rive animation
            // else display default SplashScreen.storyboard
            if (fileName != nil) {
              /**
               * Rive animation code starts here.
               */
              let mainContainer = UIView(frame: frame!)

              mainContainer.backgroundColor = UIColor(red: 0.95, green: 0.95, blue: 0.95, alpha: 1.00)

              // create rive modal
                RiveSplashScreen.riveViewModel = RiveViewModel(
                    fileName: fileName!,
                    stateMachineName: stateMachine ?? nil,
                    artboardName: artboardName ?? nil
                )

              // create rive view
              let riveView = RiveSplashScreen.riveViewModel!.createRView()

              // center rive view to the main container
              riveView.center = mainContainer.center
              riveView.frame = frame!

              // add rive view to main container.
              mainContainer.addSubview(riveView)

              RiveSplashScreen.loadingView = vc.view

              RiveSplashScreen.loadingView!.addSubview(mainContainer)

              /**
               * Rive animation code ends here.
               */
            } else {
                RiveSplashScreen.loadingView = vc.view
                // background color makes sure edges are not transparent
                // otherwise, tabs screen is visible from edges.
                RiveSplashScreen.loadingView?.backgroundColor = UIColor(red: 0.95, green: 0.95, blue: 0.95, alpha: 1.00)
                RiveSplashScreen.loadingView?.frame = frame!
            }
        }

        RiveSplashScreen.waiting = false

        rootView!.addSubview(RiveSplashScreen.loadingView!)
        RiveSplashScreen.splashScreenDisplayedAt = Date().timeIntervalSince1970;
    }

    @objc(showSplashImage:inRootView:withSplashImage:)
    public func showSplashImage(_ splashScreen: String?, inRootView rootView: UIView?, withSplashImage splashImageName: String) {
        RiveSplashScreen.nameOfSplashImageDisplayed = splashImageName

        let vc = UIStoryboard(name: "SplashScreen", bundle: nil).instantiateViewController(withIdentifier: "SplashViewController")
        var frame = rootView?.frame
        frame?.origin = CGPoint(x: 0, y: 0)

        let mainContainer = UIView(frame: frame!)
        let ivSplash = UIImageView(frame: frame!)

        let splashImage = UIImage(named: splashImageName)

        ivSplash.image = splashImage

        mainContainer.addSubview(ivSplash)

        RiveSplashScreen.loadingView = vc.view

        RiveSplashScreen.loadingView!.addSubview(mainContainer)

        RiveSplashScreen.waiting = false

        rootView!.addSubview(RiveSplashScreen.loadingView!)
    }


    //  Converted to Swift 5.7 by Swiftify v5.7.25750 - https://swiftify.com/
    @objc(hide)
    public func hide() {
        if RiveSplashScreen.waiting {
            DispatchQueue.main.async(execute: {
                RiveSplashScreen.waiting = false
            })
        } else {
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + Double(Int64(0.1 * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC), execute: {
                RiveSplashScreen.loadingView?.removeFromSuperview()
            })
        }
    }

    public func jsLoadError(_ notification: Notification?) {
        // If there was an error loading javascript, hide the splash screen so it can be shown.  Otherwise the splash screen will remain forever, which is a hassle to debug.
        self.hide()
    }

    @objc
    func constantsToExport() -> [String: Any]! {
        // number of seconds since 1970
        return [
            "splashScreenDisplayedAt": RiveSplashScreen.splashScreenDisplayedAt,
            "nameOfSplashImageDisplayed": RiveSplashScreen.nameOfSplashImageDisplayed
        ]
    }
}
