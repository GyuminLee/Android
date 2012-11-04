package com.magnitude.ARKitapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * @author Magnitude Client
 * Abstract class of static items on screen.
 */
public class MagnitudeStaticView extends View
{
		/**
		 * Is the view visible.
		 */
		private boolean visible = true;
		/**
		 * Paint object to draw Static View on the screen.
		 */
		private Paint p = new Paint();
		/**
		 * Current Application Context.
		 */
		private Context ctx;
		
		/**
		 * x position on screen.
		 */
		private float x = 0;
		/**
		 * y position on screen.
		 */
		private float y = 0;
		
		/**
		 * Constructor. This should not be called directly (only via super methods).
		 * @param ctx Current Application Context
		 */
		public MagnitudeStaticView(Context ctx)
		{
			super(ctx);
			this.setCtx(ctx);
		}
		
		/**
		 * Constructor. This should not be called directly (only via super methods).
		 * @param ctx Current Application context.
		 * @param visible Initial visible state.
		 */
		public MagnitudeStaticView(Context ctx, boolean visible)
		{
			super(ctx);
			this.setCtx(ctx);
			this.visible = visible;
		}
		
		/**
		 * Update the view's layout configuration.
		 * Use if you change x, y or intern components.
		 */
		public void updateLayout() {
		}
		
		/**
		 * Setter for X value.
		 * @param x new x value.
		 */
		public void setX(float x) {
			this.x = x;
		}
		/**
		 * Setter for Y value.
		 * @param x new y value.
		 */
		public void setY(float y) {
			this.y = y;
		}
		
		/**
		 * Draws StaticView on screen. Should not be directly used but only overridden.
		 */
		public void draw(Canvas c) {
		}
		
		/**
		 * visible getter.
		 * @return true if the view is visible, false otherwise.
		 */
		public boolean isVisible() {
			return visible;
		}
		/**
		 * visible setter
		 * @param visible new visible value.
		 */
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		/**
		 * Paint getter.
		 * @return the Paint object of the StaticView.
		 */
		public Paint getP() {
			return p;
		}
		/**
		 * Paint setter.
		 * @param p new Paint object.
		 */
		public void setP(Paint p) {
			this.p = p;
		}
		
		/**
		 * Sets the Context of current ARKitStaticView.
		 * @param ctx.
		 */
		public void setCtx(Context ctx) {
			this.ctx = ctx;
		}
		/**
		 * Gets the Context of current ARKitStaticView.
		 * @return the current Context.
		 */
		public Context getCtx() {
			return ctx;
		}

		/**
		 * Gets current x position of the view
		 * @return
		 */
		public float getX() {
			return x;
		}
		/**
		 * Gets current y position of the view.
		 * @return
		 */
		public float getY() {
			return y;
		}
	}
