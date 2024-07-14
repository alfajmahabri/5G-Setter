package in.buildbytes.a5gsetter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.ComponentName;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        set=findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Intent> intents = new ArrayList<>();
                intents.add(new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo")));
                intents.add(new Intent("android.intent.action.MAIN").setComponent(new ComponentName("com.android.phone", "com.android.phone.settings.RadioInfo")));

                boolean success = false;
                for (Intent intent : intents) {
                    if (isIntentAvailable(intent)) {
                        try {
                            startActivity(intent);
                            success = true;
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!success) {
                    Toast.makeText(MainActivity.this, "Unable to open settings. Please check your device settings.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}