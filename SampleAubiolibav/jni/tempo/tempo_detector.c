#include "com_example_sampleaubio_TempoDetector.h"
#include <aubio.h>
#include <android/log.h>

jint JNICALL Java_com_example_sampleaubio_TempoDetector_detectTempo(JNIEnv *env,
		jclass clazz, jstring jfilepath, jint samplerate, jint win_size,
		jint hop_size) {
	jboolean iscopy;
	char_t* source_path = (char_t*) (*env)->GetStringUTFChars(env, jfilepath,
			&iscopy);
	__android_log_print(ANDROID_LOG_VERBOSE, "aubio", "The filepath %s, %d, %d, %d ", source_path, samplerate, win_size, hop_size);
	int err = 0;
	aubio_source_t * source = new_aubio_source(source_path, samplerate,
			hop_size);
	if (!source) {
		err = -1;
		goto beach;
	}
	if (samplerate == 0)
		samplerate = aubio_source_get_samplerate(source);
	fvec_t * in = new_fvec(hop_size);
	fvec_t * out = new_fvec(2);

	aubio_tempo_t * o = new_aubio_tempo("default", win_size, hop_size,
			samplerate);
	float bpm = 0;
	uint_t n_frames = 0, read = 0;
	do {
		aubio_source_do(source, in, &read);
		aubio_tempo_do(o, in, out);
		if (out->data[0] != 0) {
//	      PRINT_MSG("beat at %.3fms, %.3fs, frame %d, %.2fbpm with confidence %.2f\n",
//	          aubio_tempo_get_last_ms(o), aubio_tempo_get_last_s(o),
//	          aubio_tempo_get_last(o), aubio_tempo_get_bpm(o), aubio_tempo_get_confidence(o));
//	    	bpm = aubio_tempo_get_bpm(o);
			float lbpm = aubio_tempo_get_bpm(o);
			__android_log_print(ANDROID_LOG_INFO   ,"aubio", "bpm : %f",lbpm);
			if (n_frames + read > 0 && lbpm >= 0) {
				bpm = ((bpm * n_frames) + (lbpm * read))
						/ (n_frames + read);
			}
		}
		n_frames += read;
	} while (read == hop_size);
	err = bpm;
//	  PRINT_MSG("read %.2fs, %d frames at %dHz (%d blocks) from %s\n",
//	      n_frames * 1. / samplerate,
//	      n_frames, samplerate,
//	      n_frames / hop_size, source_path);
	// clean up memory
	del_aubio_tempo(o);
	del_fvec(in);
	del_fvec(out);
	del_aubio_source(source);
	beach: aubio_cleanup();
	return err;
}
