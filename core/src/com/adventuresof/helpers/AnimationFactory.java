package com.adventuresof.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation Factory Class
 * 
 */
public class AnimationFactory {
	
	public AnimationFactory() {

	}

	/**
	 * This method generates an animation based on the given parameters
	 * @param spriteSheetName The name of the sprite sheet to be used in the animation
	 * @param frame_cols The number of columns of images in the sprite sheet
	 * @param frame_rows The number of rows of images in the sprite sheet
	 * @param numberOfImages The number of images to use for this animation, from the sprite sheet
	 * @param startingFrame
	 * @param animationFrameInterval
	 * @return
	 */
	public static Animation<TextureRegion> createAnimation(String spriteSheetName, int frame_cols, int frame_rows,
			int numberOfImages, int startingFrame, float animationFrameInterval) {
	    Texture animationSheet = new Texture(Gdx.files.internal(spriteSheetName));

		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, 
				animationSheet.getWidth() / frame_cols,
				animationSheet.getHeight() / frame_rows);

		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] animationFrames = new TextureRegion[numberOfImages];

		int index = 0;
		//image count is used to get only the required images from the sprite sheet
		int currentLoopCount = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				if(currentLoopCount >= startingFrame && currentLoopCount < startingFrame + numberOfImages) {					
					animationFrames[index++] = tmp[i][j];				
				}

				currentLoopCount++;
			}
		}

		// Initialise the Animation with the frame interval and array of frames
		return new Animation<TextureRegion>(0.08f, animationFrames);
	}

}
