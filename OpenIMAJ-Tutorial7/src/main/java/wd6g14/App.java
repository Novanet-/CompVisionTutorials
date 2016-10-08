package wd6g14;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.FFastGaussianConvolve;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.Device;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;
import org.openimaj.video.xuggle.XuggleVideo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * OpenIMAJ Hello world!
 */
public class App
{

	private static final String DL_FRAMES     = "dlFrames";
	private static final String WEBCAM_FRAMES = "webcamFrames";


	private App()
	{}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Video<MBFImage> dlVideo = null;
		Video<MBFImage> webcamVideo = null;

		try
		{
			dlVideo = new XuggleVideo(new URL("http://static.openimaj.org/media/tutorial/keyboardcat.flv"));
			final List<Device> webcams = VideoCapture.getVideoDevices();
			System.out.println(webcams);
			webcamVideo = new VideoCapture(320, 240, webcams.get(0));
		}
		catch (MalformedURLException | VideoCaptureException e)
		{
			e.printStackTrace();
		}

		final Video<MBFImage> finalDlVideo = dlVideo;
		final Video<MBFImage> finalWebcamVideo = webcamVideo;

		final Runnable dlRunnable = () ->
		{
			processDlVideo(finalDlVideo);
		};

		final Runnable webcamRunnable = () ->
		{
			processWebcamVideo(finalWebcamVideo);
		};

		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.execute(dlRunnable);
		executor.execute(webcamRunnable);

		//Unmodified video files/inputs
		//		VideoDisplay<MBFImage> dlDisplay = VideoDisplay.createVideoDisplay(dlVideo);
		//		VideoDisplay<MBFImage> webcamDisplay = VideoDisplay.createVideoDisplay(webcamVideo);

	}


	private static void processWebcamVideo(final Video<MBFImage> finalWebcamVideo)
	{
		//		if (finalWebcamVideo != null)
		//		{
		//			for (final MBFImage mbfImage : finalWebcamVideo)
		//			{
		//				final MBFImage image = mbfImage.flipX();
		//				DisplayUtilities.displayName(image.process(new CannyEdgeDetector()), WEBCAM_FRAMES);
		//			}
		//		}

		//or

		final VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(finalWebcamVideo);

		display.addVideoListener(new VideoDisplayListener<MBFImage>()
		{

			@Override
			public void beforeUpdate(MBFImage frame)
			{
				frame = frame.flipX();
				//				frame.processInplace(new CannyEdgeDetector());
				final int width = display.getScreen().getWidth();
				final int height = display.getScreen().getHeight();
				frame.processInplace(new FFastGaussianConvolve(2f, 4));
			}


			@Override
			public void afterUpdate(VideoDisplay<MBFImage> videoDisplay)
			{
			}
		});
	}


	private static void processDlVideo(final Video<MBFImage> finalDlVideo)
	{
		//		if (finalDlVideo != null)
		//		{
		//			for (final MBFImage mbfImage : finalDlVideo)
		//			{
		//				DisplayUtilities.displayName(mbfImage.process(new CannyEdgeDetector()), DL_FRAMES);
		//			}
		//		}

		//or

		VideoDisplay<MBFImage> display = null;

		if (finalDlVideo != null)
		{
			display = VideoDisplay.createVideoDisplay(finalDlVideo);
		}

		display.addVideoListener(new VideoDisplayListener<MBFImage>()
		{

			@Override
			public void beforeUpdate(MBFImage frame)
			{
				frame.processInplace(new CannyEdgeDetector());
			}


			@Override
			public void afterUpdate(VideoDisplay<MBFImage> videoDisplay)
			{
			}
		});
	}
}
