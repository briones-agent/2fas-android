package com.twofasapp

import android.os.Bundle
import com.twofasapp.brownfield.BrownfieldActivity
import com.twofasapp.brownfield.showReactNativeFragment

/**
 * Hosts the React Native screen shipped by the Expo brownfield AAR
 * (`com.twofasapp.brownfield:twofasbrownfield-fused-release`).
 *
 * [BrownfieldActivity] extends AppCompatActivity and forwards configuration
 * changes; [showReactNativeFragment] mounts the RN root fragment (module "main")
 * and wires native back-button handling.
 */
class ExpoActivity : BrownfieldActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showReactNativeFragment()
    }
}
