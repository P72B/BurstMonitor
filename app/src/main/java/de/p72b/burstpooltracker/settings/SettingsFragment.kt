package de.p72b.burstpooltracker.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import de.p72b.burstpooltracker.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}
