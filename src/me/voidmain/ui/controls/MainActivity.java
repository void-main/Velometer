package me.voidmain.ui.controls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class MainActivity extends Activity {

	private PopupWindow mPopupWindow;
	private ViewGroup mLayoutRoot;
	private View mPopupView;
	private Velometer mVelometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLayoutRoot = (ViewGroup) findViewById(R.id.layout_root);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopupView = inflater.inflate(R.layout.popup, null);
		mVelometer = (Velometer) mPopupView.findViewById(R.id.velometer);
		mVelometer.setVelometerLevelListener(listener);
		
		VelometerSlot level1 = new VelometerSlot(1, 0, 60, "1", Color.RED);
		VelometerSlot level2 = new VelometerSlot(2, 60, 120, "2", Color.MAGENTA);
		VelometerSlot level3 = new VelometerSlot(3, 120, 180, "3", Color.BLUE);
		VelometerSlot level4 = new VelometerSlot(4, 180, 240, "4", Color.CYAN);
		VelometerSlot level5 = new VelometerSlot(5, 240, 300, "5", Color.DKGRAY);
		VelometerSlot level6 = new VelometerSlot(6, 300, 360, "6", Color.GREEN);
		List<VelometerSlot> slots = new ArrayList<VelometerSlot>();
		slots.add(level1);
		slots.add(level2);
		slots.add(level3);
		slots.add(level4);
		slots.add(level5);
		slots.add(level6);
		mVelometer.setVelometerSlots(slots);

		mPopupWindow = new PopupWindow(mPopupView, Constants.OUTER_SIZE,
				Constants.OUTER_SIZE, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			mPopupWindow.showAtLocation(mLayoutRoot, Gravity.NO_GRAVITY, x
					- Constants.OUTER_SIZE / 2, y - Constants.OUTER_SIZE / 2);
			mPopupWindow.update();
			mVelometer.onShow(event);
			return false;
		case MotionEvent.ACTION_MOVE:
			mVelometer.onUpdate(event);
			return false;
		case MotionEvent.ACTION_CANCEL:
			return false;
		case MotionEvent.ACTION_UP:
			mPopupWindow.dismiss();
			return false;
		default:
			return super.onTouchEvent(event);
		}
	}

	protected IVelometerLevelListener listener = new IVelometerLevelListener() {

		@Override
		public void onLevelChanged(int level) {
			Log.e("Velo Level Changed", "" + level);
		}
	};

}
