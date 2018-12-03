package dig.big.com.appa;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TestFragment extends Fragment {

        private Button btn;
        private EditText text;
        private View rootView;
        public TestFragment() {
        }

        public static TestFragment newInstance() {
            TestFragment fragment = new TestFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_test, container, false);

            init();
            listeners();

            return rootView;
        }

        public void init(){
            text = rootView.findViewById(R.id.editText);
            btn = rootView.findViewById(R.id.button);
        }

        public void listeners(){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("dig.big.com.appb","dig.big.com.appb.MainActivity"));
                    intent.putExtra("fromA","test");
                    intent.putExtra("link",text.getText().toString());
                    startActivity(intent);
                }
            });
        }

    }

