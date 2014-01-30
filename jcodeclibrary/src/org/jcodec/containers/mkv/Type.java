package org.jcodec.containers.mkv;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jcodec.containers.mkv.ebml.BinaryElement;
import org.jcodec.containers.mkv.ebml.DateElement;
import org.jcodec.containers.mkv.ebml.Element;
import org.jcodec.containers.mkv.ebml.FloatElement;
import org.jcodec.containers.mkv.ebml.MasterElement;
import org.jcodec.containers.mkv.ebml.SignedIntegerElement;
import org.jcodec.containers.mkv.ebml.StringElement;
import org.jcodec.containers.mkv.ebml.UnsignedIntegerElement;
import org.jcodec.containers.mkv.elements.Attachments;
import org.jcodec.containers.mkv.elements.BlockElement;
import org.jcodec.containers.mkv.elements.Chapters;
import org.jcodec.containers.mkv.elements.Cluster;
import org.jcodec.containers.mkv.elements.CuePoint;
import org.jcodec.containers.mkv.elements.Info;
import org.jcodec.containers.mkv.elements.Tags;
import org.jcodec.containers.mkv.elements.TrackEntryElement;
import org.jcodec.containers.mkv.elements.Tracks;

public enum Type {
    // EBML Id's
    Void(new byte[]{(byte)0xEC}, BinaryElement.class),
    CRC32(new byte[]{(byte)0xBF}, BinaryElement.class),
    EBML(new byte[]{0x1A, 0x45, (byte)0xDF, (byte)0xA3}, MasterElement.class),
      EBMLVersion(new byte[]{0x42, (byte)0x86}),
      EBMLReadVersion(new byte[]{0x42, (byte)0xF7}),
      EBMLMaxIDLength(new byte[]{0x42, (byte)0xF2}),
      EBMLMaxSizeLength(new byte[]{0x42, (byte)0xF3}),
      //All strings are UTF8 in java, this element is specified as pure ASCII element in Matroska spec
      DocType(new byte[]{0x42, (byte)0x82}, StringElement.class), 
      DocTypeVersion(new byte[]{0x42, (byte)0x87}),
      DocTypeReadVersion(new byte[]{0x42, (byte)0x85}),

    Segment(new byte[]{0x18, 0x53, (byte)0x80, 0x67}, MasterElement.class),
      SeekHead(new byte[]{0x11, 0x4D, (byte)0x9B, 0x74}, MasterElement.class),
        Seek(new byte[]{0x4D, (byte)0xBB}, MasterElement.class),
          SeekID(new byte[]{0x53, (byte)0xAB}, BinaryElement.class),
          SeekPosition(new byte[]{0x53, (byte)0xAC}),
      Info(new byte[]{0x15, (byte)0x49, (byte)0xA9, (byte)0x66}, Info.class),
        SegmentUID(new byte[]{0x73, (byte)0xA4}, BinaryElement.class),
        //All strings are UTF8 in java
        SegmentFilename(new byte[]{0x73, (byte)0x84}, StringElement.class), 
        PrevUID(new byte[]{0x3C, (byte)0xB9, 0x23}, BinaryElement.class),
        PrevFilename(new byte[]{0x3C, (byte)0x83, (byte)0xAB}, StringElement.class),
        NextUID(new byte[]{0x3E, (byte)0xB9, 0x23}, BinaryElement.class),
        //An escaped filename corresponding to the next segment.
        NextFilenam(new byte[]{0x3E, (byte)0x83, (byte)0xBB}, StringElement.class), 
        // A randomly generated unique ID that all segments related to each other must use (128 bits).
        SegmentFamily(new byte[]{0x44,0x44}, BinaryElement.class), 
        // A tuple of corresponding ID used by chapter codecs to represent this segment.
        ChapterTranslate(new byte[]{0x69,0x24}, MasterElement.class), 
            //Specify an edition UID on which this correspondance applies. When not specified, it means for all editions found in the segment.
            ChapterTranslateEditionUID(new byte[]{0x69, (byte) 0xFC}), 
            //The chapter codec using this ID (0: Matroska Script, 1: DVD-menu).
            ChapterTranslateCodec(new byte[]{0x69, (byte) 0xBF}), 
            // The binary value used to represent this segment in the chapter codec data. The format depends on the ChapProcessCodecID used.
            ChapterTranslateID(new byte[]{0x69, (byte)0xA5}, BinaryElement.class), 
        // Timecode scale in nanoseconds (1.000.000 means all timecodes in the segment are expressed in milliseconds).
        // Every timecode of a block (cluster timecode + block timecode ) is multiplied by this value to obtain real timecode of a block
        TimecodeScale(new byte[]{0x2A, (byte)0xD7, (byte)0xB1}),
        
        Duration(new byte[]{0x44, (byte)0x89}, FloatElement.class),
        DateUTC(new byte[]{0x44, (byte)0x61}, DateElement.class),
        Title(new byte[]{0x7B, (byte)0xA9}, StringElement.class),
        MuxingApp(new byte[]{0x4D, (byte)0x80}, StringElement.class),
        WritingApp(new byte[]{0x57,       0x41}, StringElement.class),
       
     //The lower level element containing the (monolithic) Block structure.
     Cluster(new byte[]{0x1F, (byte)0x43, (byte)0xB6, (byte)0x75}, Cluster.class), 
         //Absolute timecode of the cluster (based on TimecodeScale).
         Timecode(new byte[]{(byte)0xE7}), 
         //  The list of tracks that are not used in that part of the stream. It is useful when using overlay tracks on seeking. Then you should decide what track to use.
         SilentTracks(new byte[]{0x58, 0x54}, MasterElement.class), 
             //  One of the track number that are not used from now on in the stream. It could change later if not specified as silent in a further Cluster
             SilentTrackNumber(new byte[]{0x58, (byte)0xD7}), 
         //  The Position of the Cluster in the segment (0 in live broadcast streams). It might help to resynchronise offset on damaged streams.
         Position(new byte[]{(byte)0xA7}),  
         // Size of the previous Cluster, in octets. Can be useful for backward playing.
         PrevSize(new byte[]{(byte)0xAB}),  
         //Similar to Block but without all the extra information, mostly used to reduced overhead when no extra feature is needed. (see SimpleBlock Structure)
         SimpleBlock(new byte[]{(byte)0xA3}, BlockElement.class), 
         //Basic container of information containing a single Block or BlockVirtual, and information specific to that Block/VirtualBlock.
         BlockGroup(new byte[]{(byte)0xA0}, MasterElement.class), 
           // Block containing the actual data to be rendered and a timecode relative to the Cluster Timecode. (see Block Structure)
           Block(new byte[]{(byte)0xA1}, BlockElement.class), 
           // Contain additional blocks to complete the main one. An EBML parser that has no knowledge of the Block structure could still see and use/skip these data.
           BlockAdditions(new byte[]{0x75, (byte) 0xA1}, MasterElement.class), 
               //  Contain the BlockAdditional and some parameters.
               BlockMore(new byte[]{(byte)0xA6}, MasterElement.class), 
                   //  An ID to identify the BlockAdditional level.
                   BlockAddID(new byte[]{(byte)0xEE}), 
                   // Interpreted by the codec as it wishes (using the BlockAddID).
                   BlockAdditional(new byte[]{(byte) 0xA5}, BinaryElement.class), 
          /**
           * The duration of the Block (based on TimecodeScale). 
           * This element is mandatory when DefaultDuration is set for the track (but can be omitted as other default values). 
           * When not written and with no DefaultDuration, the value is assumed to be the difference between the timecode 
           * of this Block and the timecode of the next Block in "display" order (not coding order). 
           * This element can be useful at the end of a Track (as there is not other Block available), 
           * or when there is a break in a track like for subtitle tracks. 
           * When set to 0 that means the frame is not a keyframe.
           */
           BlockDuration(new byte[]{(byte)0x9B}),
           // This frame is referenced and has the specified cache priority. In cache only a frame of the same or higher priority can replace this frame. A value of 0 means the frame is not referenced
           ReferencePriority(new byte[]{(byte) 0xFA}),  
           //Timecode of another frame used as a reference (ie: B or P frame). The timecode is relative to the block it's attached to.
           ReferenceBlock(new byte[]{(byte)0xFB}, SignedIntegerElement.class), 
           //  The new codec state to use. Data interpretation is private to the codec. This information should always be referenced by a seek entry.
           CodecState(new byte[]{(byte)0xA4}, BinaryElement.class), 
           //  Contains slices description.
           Slices(new byte[]{(byte)0x8E}, MasterElement.class),
               //  Contains extra time information about the data contained in the Block. While there are a few files in the wild with this element, it is no longer in use and has been deprecated. Being able to interpret this element is not required for playback.
               TimeSlice(new byte[]{(byte)0xE8}, MasterElement.class), 
                   //  The reverse number of the frame in the lace (0 is the last frame, 1 is the next to last, etc). While there are a few files in the wild with this element, it is no longer in use and has been deprecated. Being able to interpret this element is not required for playback.
                   LaceNumber(new byte[]{(byte)0xCC}), 
          
       //A top-level block of information with many tracks described.
      Tracks(new byte[]{0x16, (byte)0x54, (byte)0xAE, (byte)0x6B}, Tracks.class), 
        //Describes a track with all elements.
        TrackEntry(new byte[]{(byte)0xAE}, TrackEntryElement.class), 
          //The track number as used in the Block Header (using more than 127 tracks is not encouraged, though the design allows an unlimited number).
          TrackNumber(new byte[]{(byte)0xD7}), 
          //A unique ID to identify the Track. This should be kept the same when making a direct stream copy of the Track to another file.
          TrackUID(new byte[]{0x73, (byte)0xC5}), 
          //A set of track types coded on 8 bits (1: video, 2: audio, 3: complex, 0x10: logo, 0x11: subtitle, 0x12: buttons, 0x20: control).
          TrackType(new byte[]{(byte)0x83}), 
          //  Set if the track is usable. (1 bit)
          FlagEnabled(new byte[]{(byte)0xB9}), 
          //  Set if that track (audio, video or subs) SHOULD be active if no language found matches the user preference. (1 bit)
          FlagDefault(new byte[]{(byte)0x88}), 
          //   Set if that track MUST be active during playback. There can be many forced track for a kind (audio, video or subs), the player should select the one which language matches the user preference or the default + forced track. Overlay MAY happen between a forced and non-forced track of the same kind. (1 bit)
          FlagForced(new byte[]{0x55,(byte)0xAA}), 
          //   Set if the track may contain blocks using lacing. (1 bit)
          FlagLacing(new byte[]{(byte)0x9C}), 
          //  The minimum number of frames a player should be able to cache during playback. If set to 0, the reference pseudo-cache system is not used.
          MinCache(new byte[]{0x6D,(byte)0xE7}), 
          //  The maximum cache size required to store referenced frames in and the current frame. 0 means no cache is needed.
          MaxCache(new byte[]{0x6D,(byte)0xF8}), 
          //Number of nanoseconds (not scaled via TimecodeScale) per frame ('frame' in the Matroska sense -- one element put into a (Simple)Block).
          DefaultDuration(new byte[]{0x23, (byte)0xE3, (byte)0x83}), 
          // The maximum value of BlockAddID. A value 0 means there is no BlockAdditions for this track.
          MaxBlockAdditionID(new byte[]{0x55, (byte)0xEE}), 
          //A human-readable track name.
          Name(new byte[]{0x53, 0x6E}, StringElement.class), 
          // Specifies the language of the track in the Matroska languages form.
          Language(new byte[]{0x22, (byte)0xB5, (byte)0x9C}, StringElement.class), 
          // An ID corresponding to the codec, see the codec page for more info.
          CodecID(new byte[]{(byte)0x86}, StringElement.class), 
          //Private data only known to the codec.
          CodecPrivate(new byte[]{(byte)0x63, (byte)0xA2}, BinaryElement.class), 
          //  A human-readable string specifying the codec.
          CodecName(new byte[]{(byte)0x25,(byte)0x86,(byte)0x88}, StringElement.class), 
          //  The UID of an attachment that is used by this codec.
          AttachmentLink(new byte[]{0x74, 0x46}), 
          // The codec can decode potentially damaged data (1 bit).
          CodecDecodeAll(new byte[]{(byte)0xAA}), 
          //  Specify that this track is an overlay track for the Track specified (in the u-integer). That means when this track has a gap (see SilentTracks) the overlay track should be used instead. The order of multiple TrackOverlay matters, the first one is the one that should be used. If not found it should be the second, etc.
          TrackOverlay(new byte[]{0x6F,(byte)0xAB}), 
          // The track identification for the given Chapter Codec.
          TrackTranslate(new byte[]{0x66, 0x24}, MasterElement.class), 
              //  Specify an edition UID on which this translation applies. When not specified, it means for all editions found in the segment.
              TrackTranslateEditionUID(new byte[]{0x66,(byte)0xFC}), 
              //  The chapter codec using this ID (0: Matroska Script, 1: DVD-menu).
              TrackTranslateCodec(new byte[]{0x66,(byte)0xBF}), 
              // The binary value used to represent this track in the chapter codec data. The format depends on the ChapProcessCodecID used.
              TrackTranslateTrackID(new byte[]{0x66,(byte)0xA5}, BinaryElement.class), 
          Video(new byte[]{(byte)0xE0}, MasterElement.class),
              // Set if the video is interlaced. (1 bit)
              FlagInterlaced(new byte[]{(byte)0x9A}), 
              // Stereo-3D video mode (0: mono, 1: side by side (left eye is first), 2: top-bottom (right eye is first), 3: top-bottom (left eye is first), 4: checkboard (right is first), 5: checkboard (left is first), 6: row interleaved (right is first), 7: row interleaved (left is first), 8: column interleaved (right is first), 9: column interleaved (left is first), 10: anaglyph (cyan/red), 11: side by side (right eye is first), 12: anaglyph (green/magenta), 13 both eyes laced in one Block (left eye is first), 14 both eyes laced in one Block (right eye is first)) . There are some more details on 3D support in the Specification Notes.
              StereoMode(new byte[]{0x53,(byte)0xB8}), 
              // Alpha Video Mode. Presence of this element indicates that the BlockAdditional element could contain Alpha data.
              AlphaMode(new byte[]{0x53,(byte)0xC0}), 
              PixelWidth(new byte[]{(byte)0xB0}),
              PixelHeight(new byte[]{(byte)0xBA}),
              //  The number of video pixels to remove at the bottom of the image (for HDTV content).
              PixelCropBottom(new byte[]{0x54,(byte)0xAA}),   
              // The number of video pixels to remove at the top of the image.
              PixelCropTop(new byte[]{0x54,(byte)0xBB}),  
              // The number of video pixels to remove on the left of the image.
              PixelCropLeft(new byte[]{0x54,(byte)0xCC}),  
              //  The number of video pixels to remove on the right of the image.
              PixelCropRight(new byte[]{0x54,(byte)0xDD}),  
              DisplayWidth(new byte[]{0x54, (byte)0xB0}),
              DisplayHeight(new byte[]{0x54, (byte)0xBA}),
              //  How DisplayWidth & DisplayHeight should be interpreted (0: pixels, 1: centimeters, 2: inches, 3: Display Aspect Ratio).
              DisplayUnit(new byte[]{0x54,(byte)0xB2}), 
              //  Specify the possible modifications to the aspect ratio (0: free resizing, 1: keep aspect ratio, 2: fixed).
              AspectRatioType(new byte[]{0x54,(byte)0xB3}), 
              //  Same value as in AVI (32 bits).
              ColourSpace(new byte[]{0x2E, (byte)0xB5,0x24}, BinaryElement.class), 
          Audio(new byte[]{(byte)0xE1}, MasterElement.class),
              SamplingFrequency(new byte[]{(byte)0xB5}, FloatElement.class),
              OutputSamplingFrequency(new byte[]{0x78, (byte)0xB5}, FloatElement.class),
              Channels(new byte[]{(byte)0x9F}),
              BitDepth(new byte[]{0x62, 0x64}),
          //  Operation that needs to be applied on tracks to create this virtual track. For more details look at the Specification Notes on the subject.
          TrackOperation(new byte[]{(byte)0xE2}, MasterElement.class),  
              // Contains the list of all video plane tracks that need to be combined to create this 3D track
              TrackCombinePlanes(new byte[]{(byte)0xE3}, MasterElement.class), 
                  //  Contains a video plane track that need to be combined to create this 3D track
                  TrackPlane(new byte[]{(byte)0xE4}, MasterElement.class), 
                      //  The trackUID number of the track representing the plane.
                      TrackPlaneUID(new byte[]{(byte)0xE5}), 
                      //  The kind of plane this track corresponds to (0: left eye, 1: right eye, 2: background).
                      TrackPlaneType(new byte[]{(byte)0xE6}), 
               // Contains the list of all tracks whose Blocks need to be combined to create this virtual track
              TrackJoinBlocks(new byte[]{(byte)0xE9}, MasterElement.class), 
                  // The trackUID number of a track whose blocks are used to create this virtual track.
                  TrackJoinUID(new byte[]{(byte)0xED}), 
          // Settings for several content encoding mechanisms like compression or encryption.
          ContentEncodings(new byte[]{0x6D, (byte)0x80}, MasterElement.class), 
              // Settings for one content encoding like compression or encryption.
              ContentEncoding(new byte[]{0x62, 0x40}, MasterElement.class), 
                  // Tells when this modification was used during encoding/muxing starting with 0 and counting upwards. The decoder/demuxer has to start with the highest order number it finds and work its way down. This value has to be unique over all ContentEncodingOrder elements in the segment.
                  ContentEncodingOrder(new byte[]{0x50, 0x31}), 
                  //  A bit field that describes which elements have been modified in this way. Values (big endian) can be OR'ed. Possible values: 1 - all frame contents, 2 - the track's private data, 4 - the next ContentEncoding (next ContentEncodingOrder. Either the data inside ContentCompression and/or ContentEncryption)
                  ContentEncodingScope(new byte[]{0x50, 0x32}), 
                  //   A value describing what kind of transformation has been done. Possible values: 0 - compression, 1 - encryption
                  ContentEncodingType(new byte[]{0x50, 0x33}), 
                  // Settings describing the compression used. Must be present if the value of ContentEncodingType is 0 and absent otherwise. Each block must be decompressable even if no previous block is available in order not to prevent seeking.
                  ContentCompression(new byte[]{0x50, 0x34}, MasterElement.class), 
                  //  The compression algorithm used. Algorithms that have been specified so far are: 0 - zlib, 1 - bzlib, 2 - lzo1x, 3 - Header Stripping
                      ContentCompAlgo(new byte[]{0x42, (byte)0x54}), 
                      //  Settings that might be needed by the decompressor. For Header Stripping (ContentCompAlgo=3), the bytes that were removed from the beggining of each frames of the track.
                      ContentCompSettings(new byte[]{0x42, 0x55}, BinaryElement.class), 
                      //  Settings describing the encryption used. Must be present if the value of ContentEncodingType is 1 and absent otherwise.
                  ContentEncryption(new byte[]{0x50, 0x35}, MasterElement.class), 
                  //  The encryption algorithm used. The value '0' means that the contents have not been encrypted but only signed. Predefined values: 1 - DES, 2 - 3DES, 3 - Twofish, 4 - Blowfish, 5 - AES
                      ContentEncAlgo(new byte[]{0x47, (byte)0xE1}), 
                      // For public key algorithms this is the ID of the public key the the data was encrypted with.
                      ContentEncKeyID(new byte[]{0x47, (byte)0xE2}, BinaryElement.class), 
                      //  A cryptographic signature of the contents.
                      ContentSignature(new byte[]{0x47, (byte)0xE3}, BinaryElement.class), 
                      //  This is the ID of the private key the data was signed with.
                      ContentSigKeyID(new byte[]{0x47, (byte)0xE4}, BinaryElement.class), 
                      // The algorithm used for the signature. A value of '0' means that the contents have not been signed but only encrypted. Predefined values: 1 - RSA
                      ContentSigAlgo(new byte[]{0x47, (byte)0xE5}), 
                      // The hash algorithm used for the signature. A value of '0' means that the contents have not been signed but only encrypted. Predefined values: 1 - SHA1-160, 2 - MD5
                      ContentSigHashAlgo(new byte[]{0x47, (byte)0xE6}), 
                    
        Cues(new byte[]{0x1C,0x53,(byte)0xBB,0x6B}, MasterElement.class),
           CuePoint(new byte[]{(byte)0xBB}, CuePoint.class),
               CueTime(new byte[]{(byte)0xB3}),
               CueTrackPositions(new byte[]{(byte)0xB7}, MasterElement.class),
                   CueTrack(new byte[]{(byte)0xF7}),
                   CueClusterPosition(new byte[]{(byte)0xF1}),
                   // The relative position of the referenced block inside the cluster with 0 being the first possible position for an element inside that cluster.
                   CueRelativePosition(new byte[]{(byte)0xF0}), 
                   // The duration of the block according to the segment time base. If missing the track's DefaultDuration does not apply and no duration information is available in terms of the cues.
                   CueDuration(new byte[]{(byte)0xB2}), 
                   //Number of the Block in the specified Cluster.
                   CueBlockNumber(new byte[]{0x53, 0x78}), 
                   // The position of the Codec State corresponding to this Cue element. 0 means that the data is taken from the initial Track Entry.
                   CueCodecState(new byte[]{(byte)0xEA}), 
                   // The Clusters containing the required referenced Blocks.
                   CueReference(new byte[]{(byte)0xDB}, MasterElement.class), 
                   // Timecode of the referenced Block.
                       CueRefTime(new byte[]{(byte)0x96}),

      Attachments(new byte[]{0x19, 0x41, (byte)0xA4, 0x69}, Attachments.class),
        AttachedFile(new byte[]{0x61, (byte)0xA7}, MasterElement.class),
          FileDescription(new byte[]{0x46, (byte)0x7E}, StringElement.class),
          FileName(new byte[]{0x46, (byte)0x6E}, StringElement.class),
          FileMimeType(new byte[]{0x46, (byte)0x60}, StringElement.class),
          FileData(new byte[]{0x46, (byte)0x5C}, BinaryElement.class),
          FileUID(new byte[]{0x46, (byte)0xAE}),

     Chapters(new byte[]{0x10, (byte)0x43, (byte)0xA7, (byte)0x70}, Chapters.class),
      EditionEntry(new byte[]{(byte)0x45, (byte)0xB9}, MasterElement.class),
        EditionUID(new byte[]{(byte)0x45, (byte)0xBC}),
        EditionFlagHidden(new byte[]{(byte)0x45, (byte)0xBD}),
        EditionFlagDefault(new byte[]{(byte)0x45, (byte)0xDB}),
        EditionFlagOrdered(new byte[]{(byte)0x45, (byte)0xDD}),
        ChapterAtom(new byte[]{(byte)0xB6}, MasterElement.class),
          ChapterUID(new byte[]{(byte)0x73, (byte)0xC4}),
            //  A unique string ID to identify the Chapter. Use for WebVTT cue identifier storage.
          ChapterStringUID(new byte[]{0x56,0x54}, StringElement.class), 
          ChapterTimeStart(new byte[]{(byte)0x91}),
          ChapterTimeEnd(new byte[]{(byte)0x92}),
          ChapterFlagHidden(new byte[]{(byte)0x98}),
          ChapterFlagEnabled(new byte[]{(byte)0x45, (byte)0x98}),
          //  A segment to play in place of this chapter. Edition ChapterSegmentEditionUID should be used for this segment, otherwise no edition is used.
          ChapterSegmentUID(new byte[]{0x6E,0x67}, BinaryElement.class), 
          //  The EditionUID to play from the segment linked in ChapterSegmentUID.
          ChapterSegmentEditionUID(new byte[]{0x6E,(byte)0xBC}), 
          ChapterPhysicalEquiv(new byte[]{(byte)0x63, (byte)0xC3}),
          ChapterTrack(new byte[]{(byte)0x8F}, MasterElement.class),
            ChapterTrackNumber(new byte[]{(byte)0x89}),
          ChapterDisplay(new byte[]{(byte)0x80}, MasterElement.class),
            ChapString(new byte[]{(byte)0x85}, StringElement.class),
            ChapLanguage(new byte[]{(byte)0x43, (byte)0x7C}, StringElement.class),
            ChapCountry(new byte[]{(byte)0x43, (byte)0x7E}, StringElement.class),
            // Contains all the commands associated to the Atom.
          ChapProcess(new byte[]{0x69, 0x44}, MasterElement.class), 
          // Contains the type of the codec used for the processing. A value of 0 means native Matroska processing (to be defined), a value of 1 means the DVD command set is used. More codec IDs can be added later.
            ChapProcessCodecID(new byte[]{0x69, 0x55}), 
            // Some optional data attached to the ChapProcessCodecID information. For ChapProcessCodecID = 1, it is the "DVD level" equivalent.
            ChapProcessPrivate(new byte[]{0x45, 0x0D}, BinaryElement.class), 
            // Contains all the commands associated to the Atom.
            ChapProcessCommand(new byte[]{0x69, 0x11}, MasterElement.class), 
            // Defines when the process command should be handled (0: during the whole chapter, 1: before starting playback, 2: after playback of the chapter).
              ChapProcessTime(new byte[]{0x69, 0x22}), 
              // Contains the command information. The data should be interpreted depending on the ChapProcessCodecID value. For ChapProcessCodecID = 1, the data correspond to the binary DVD cell pre/post commands.
              ChapProcessData(new byte[]{0x69, 0x33}, BinaryElement.class), 
              
      Tags(new byte[]{0x12, (byte)0x54, (byte)0xC3, (byte)0x67}, Tags.class),
          Tag(new byte[]{0x73, (byte)0x73}, MasterElement.class),
              Targets(new byte[]{0x63, (byte)0xC0}, MasterElement.class),
                  TargetTypeValue(new byte[]{0x68, (byte) 0xCA}),
                  TargetType(new byte[]{0x63,(byte)0xCA}, StringElement.class),
                  TagTrackUID(new byte[]{0x63, (byte)0xC5}),
                  //  A unique ID to identify the EditionEntry(s) the tags belong to. If the value is 0 at this level, the tags apply to all editions in the Segment.
                  TagEditionUID(new byte[]{0x63,(byte)0xC9}), 
                  TagChapterUID(new byte[]{0x63, (byte)0xC4}),
                  TagAttachmentUID(new byte[]{0x63, (byte)0xC6}),
              SimpleTag(new byte[]{0x67, (byte)0xC8}, MasterElement.class),
                  TagName(new byte[]{0x45, (byte)0xA3}, StringElement.class),
                  TagLanguage(new byte[]{0x44, 0x7A}, StringElement.class),
                  TagDefault(new byte[]{0x44, (byte)0x84}),
                  TagString(new byte[]{0x44, (byte)0x87}, StringElement.class),
                  TagBinary(new byte[]{0x44, (byte)0x85}, BinaryElement.class);
      
      static public Type[] firstLevelHeaders = {SeekHead, Info, Cluster, Tracks, Cues, Attachments, Chapters, Tags, EBMLVersion, EBMLReadVersion, EBMLMaxIDLength, EBMLMaxSizeLength, DocType, DocTypeVersion, DocTypeReadVersion };
      public final byte [] id;
      public final Class<? extends Element> clazz;
      
      private Type(byte[] id){
          this.id = id;
          this.clazz = UnsignedIntegerElement.class;
      }
      
      private Type(byte[] id, Class<? extends Element> clazz){
          this.id = id;
          this.clazz = clazz;
      }
      
        @SuppressWarnings("unchecked")
        public static <T extends Element> T createElementByType(Type g) {
            try {
                T elem = (T) createElement(g.clazz, g.id);
                elem.type = g;
                return elem;
            } catch (SecurityException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            } catch (InstantiationException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return (T) new BinaryElement(g.id);
            }
        }

      private static <T extends Element> T createElement(Class<T> clazz, byte[] id) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InstantiationException,
              IllegalAccessException {
          Constructor<T> c = clazz.getConstructor(byte[].class);
          return c.newInstance(id);
      }
      
      @SuppressWarnings("unchecked")
      public static <T extends Element> T createElementById(byte[] id) {
          for (Type t : Type.values()){
              if (Arrays.equals(t.id, id))
                  return createElementByType(t);
                
          }
          System.err.println("Non standard element ID encountered: "+Reader.printAsHex(id));
          T t = (T) new BinaryElement(id);
          t.type = Type.Void;
        return t;
      }
      
      public static boolean isHeaderFirstByte(byte b){
          for(Type t : Type.values())
              if (t.id[0] == b)
                  return true;
          
          return false;
      }
      
      public static boolean isSpecifiedHeader(byte[] b){
          for (Type firstLevelHeader : Type.values())
              if (Arrays.equals(firstLevelHeader.id, b))
                  return true;
          return false;
      }
      
      public static boolean isFirstLevelHeader(byte[] b){
          for (Type firstLevelHeader : firstLevelHeaders)
              if (Arrays.equals(firstLevelHeader.id, b))
                  return true;
          return false;
      }
      
      public static final Map<Type, Set<Type>> children = new HashMap<Type, Set<Type>>();
      static {
          children.put(EBML, new HashSet<Type>(Arrays.asList(new Type[]{EBMLVersion, EBMLReadVersion, EBMLMaxIDLength, EBMLMaxSizeLength, DocType, DocTypeVersion, DocTypeReadVersion})));
          children.put(Segment, new HashSet<Type>(Arrays.asList(new Type[]{SeekHead, Info, Cluster, Tracks, Cues, Attachments, Chapters, Tags})));
          
          children.put(SeekHead, new HashSet<Type>(Arrays.asList(new Type[]{Seek})));
          children.put(Seek, new HashSet<Type>(Arrays.asList(new Type[]{SeekID, SeekPosition})));
          children.put(Info, new HashSet<Type>(Arrays.asList(new Type[]{SegmentUID, SegmentFilename, PrevUID, PrevFilename, NextUID, NextFilenam, SegmentFamily, ChapterTranslate, TimecodeScale, Duration , DateUTC, Title, MuxingApp, WritingApp})));
          children.put(ChapterTranslate, new HashSet<Type>(Arrays.asList(new Type[]{ChapterTranslateEditionUID, ChapterTranslateCodec, ChapterTranslateID})));
          
          children.put(Cluster, new HashSet<Type>(Arrays.asList(new Type[]{Timecode, SilentTracks, Position, PrevSize, SimpleBlock, BlockGroup})));
          children.put(SilentTracks, new HashSet<Type>(Arrays.asList(new Type[]{SilentTrackNumber})));
          children.put(BlockGroup, new HashSet<Type>(Arrays.asList(new Type[]{Block, BlockAdditions, BlockDuration, ReferencePriority, ReferenceBlock, CodecState, Slices})));
          children.put(BlockAdditions, new HashSet<Type>(Arrays.asList(new Type[]{BlockMore})));
          children.put(BlockMore, new HashSet<Type>(Arrays.asList(new Type[]{BlockAddID, BlockAdditional})));
          children.put(Slices, new HashSet<Type>(Arrays.asList(new Type[]{TimeSlice})));
          children.put(TimeSlice, new HashSet<Type>(Arrays.asList(new Type[]{LaceNumber})));

          children.put(Tracks, new HashSet<Type>(Arrays.asList(new Type[]{TrackEntry})));
          children.put(TrackEntry, new HashSet<Type>(Arrays.asList(new Type[]{TrackNumber, TrackUID, TrackType, TrackType, FlagDefault, FlagForced, FlagLacing, MinCache, MaxCache, DefaultDuration, MaxBlockAdditionID, Name, Language, CodecID, CodecPrivate, CodecName, AttachmentLink, CodecDecodeAll, TrackOverlay, TrackTranslate, Video, Audio, TrackOperation, ContentEncodings})));
          children.put(TrackTranslate, new HashSet<Type>(Arrays.asList(new Type[]{TrackTranslateEditionUID, TrackTranslateCodec, TrackTranslateTrackID})));
          children.put(Video, new HashSet<Type>(Arrays.asList(new Type[]{FlagInterlaced, StereoMode, AlphaMode, PixelWidth, PixelHeight, PixelCropBottom, PixelCropTop, PixelCropLeft, PixelCropRight, DisplayWidth, DisplayHeight, DisplayUnit, AspectRatioType, ColourSpace})));
          children.put(Audio, new HashSet<Type>(Arrays.asList(new Type[]{SamplingFrequency, OutputSamplingFrequency, Channels, BitDepth})));
          children.put(TrackOperation, new HashSet<Type>(Arrays.asList(new Type[]{TrackCombinePlanes, TrackJoinBlocks})));
          children.put(TrackCombinePlanes, new HashSet<Type>(Arrays.asList(new Type[]{TrackPlane})));
          children.put(TrackPlane, new HashSet<Type>(Arrays.asList(new Type[]{TrackPlaneUID, TrackPlaneType})));
          children.put(TrackJoinBlocks, new HashSet<Type>(Arrays.asList(new Type[]{TrackJoinUID})));
          children.put(ContentEncodings, new HashSet<Type>(Arrays.asList(new Type[]{ContentEncoding})));
          children.put(ContentEncoding, new HashSet<Type>(Arrays.asList(new Type[]{ContentEncodingOrder, ContentEncodingScope, ContentEncodingType, ContentCompression, ContentEncryption})));
          children.put(ContentCompression, new HashSet<Type>(Arrays.asList(new Type[]{ContentCompAlgo, ContentCompSettings})));
          children.put(ContentEncryption, new HashSet<Type>(Arrays.asList(new Type[]{ContentEncAlgo, ContentEncKeyID, ContentSignature, ContentSigKeyID, ContentSigAlgo, ContentSigHashAlgo})));
          
          children.put(Cues, new HashSet<Type>(Arrays.asList(new Type[]{CuePoint})));
          children.put(CuePoint, new HashSet<Type>(Arrays.asList(new Type[]{CueTime, CueTrackPositions})));
          children.put(CueTrackPositions, new HashSet<Type>(Arrays.asList(new Type[]{CueTrack, CueClusterPosition, CueRelativePosition, CueDuration, CueBlockNumber, CueCodecState, CueReference})));
          children.put(CueReference, new HashSet<Type>(Arrays.asList(new Type[]{CueRefTime})));
          
          children.put(Attachments, new HashSet<Type>(Arrays.asList(new Type[]{AttachedFile})));
          children.put(AttachedFile, new HashSet<Type>(Arrays.asList(new Type[]{FileDescription, FileName, FileMimeType, FileData, FileUID})));
          
          children.put(Chapters, new HashSet<Type>(Arrays.asList(new Type[]{EditionEntry})));
          children.put(EditionEntry, new HashSet<Type>(Arrays.asList(new Type[]{EditionUID, EditionFlagHidden, EditionFlagDefault, EditionFlagOrdered, ChapterAtom})));
          children.put(ChapterAtom, new HashSet<Type>(Arrays.asList(new Type[]{ChapterUID, ChapterStringUID, ChapterTimeStart, ChapterTimeEnd, ChapterFlagHidden, ChapterFlagEnabled, ChapterSegmentUID, ChapterSegmentEditionUID, ChapterPhysicalEquiv, ChapterTrack, ChapterDisplay, ChapProcess})));
          children.put(ChapterTrack, new HashSet<Type>(Arrays.asList(new Type[]{ChapterTrackNumber})));
          children.put(ChapterDisplay, new HashSet<Type>(Arrays.asList(new Type[]{ChapString, ChapLanguage, ChapCountry})));
          children.put(ChapProcess, new HashSet<Type>(Arrays.asList(new Type[]{ChapProcessCodecID, ChapProcessPrivate, ChapProcessCommand})));
          children.put(ChapProcessCommand, new HashSet<Type>(Arrays.asList(new Type[]{ChapProcessTime, ChapProcessData})));
          
          children.put(Tags, new HashSet<Type>(Arrays.asList(new Type[]{Tag})));
          children.put(Tag, new HashSet<Type>(Arrays.asList(new Type[]{Targets, SimpleTag})));
          children.put(Targets, new HashSet<Type>(Arrays.asList(new Type[]{TargetTypeValue, TargetType, TagTrackUID, TagEditionUID, TagChapterUID, TagAttachmentUID})));
          children.put(SimpleTag, new HashSet<Type>(Arrays.asList(new Type[]{TagName, TagLanguage, TagDefault, TagString, TagBinary})));
      }
      
      // TODO: this may be optimized of-course if it turns out to be a frequently called method
      public static Type getParent(Type t){
          for(Entry<Type, Set<Type>> ent : children.entrySet()){
              if (ent.getValue().contains(t))
                  return ent.getKey();
          }
          return null;
      }

    public static boolean possibleChild(MasterElement parent, Element child) {
        if (parent == null)
            if (child.type == EBML || child.type == Segment)
                return true;
            else
                return false;
        
        // Any unknown element is assigned type of Void by parses, thus two different checks
        
        // 1. since Void/CRC32 can occur anywhere in the tree, 
        //     look if they violate size-offset  contract of the parent.
        //     Violated size-offset contract implies the global element actually belongs to parent        
        if (Arrays.equals(child.id, Type.Void.id) || Arrays.equals(child.id, Type.CRC32.id))
            return !(child.offset == (parent.dataOffset+parent.size));
        
        // 2. In case Void/CRC32 type is assigned, child element is assumed as global,
        //    thus it can appear anywhere in the tree
        if (child.type == Void || child.type == CRC32)
            return true;
        
        Set<Type> candidates = children.get(parent.type);
        return candidates != null && candidates.contains(child.type);
    }
    
    public static boolean possibleChild(MasterElement parent, byte[] typeId) {
        // Only EBML or Segment are allowed at top level
        if (parent == null && (Arrays.equals(EBML.id, typeId) || Arrays.equals(Segment.id, typeId)))
            return true;
        
        // Other elements at top level are not allowed
        if (parent == null)
            return false;
        
        // Void and CRC32 elements are global and are allowed everywhere in the hierarchy
        if (Arrays.equals(Void.id, typeId) || Arrays.equals(CRC32.id, typeId))
            return true;
        
        // for any other element we have to check the spec
        for(Type aCandidate : children.get(parent.type))
            if (Arrays.equals(aCandidate.id, typeId))
                return true;
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public static Element findFirst(Element master, Type... path){
        List<Type> tlist = new LinkedList<Type>(Arrays.asList(path));        
        return findFirstSub(master, tlist);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T findFirst(List<? extends Element> tree, Type... path){
        List<Type> tlist = new LinkedList<Type>(Arrays.asList(path));
        for(Element e : tree){
            Element z = findFirstSub(e, tlist);
            if (z != null)
                return (T) z;
        }
            
        return null;
    }

    private static Element findFirstSub(Element elem, List<Type> path) {
        if (path.size() == 0)
            return null;
        
        if (!elem.type.equals(path.get(0)))
            return null;
        
        if (path.size() == 1)
            return elem;
        
        Type head = path.remove(0);
        Element result = null;
        if (elem instanceof MasterElement) {
            Iterator<Element> iter = ((MasterElement) elem).children.iterator();
            while(iter.hasNext() && result == null) 
                result = findFirstSub(iter.next(), path);
            
        }
        
        path.add(0, head);
        return result;
        
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] findAll(List<? extends Element> tree, Class<T> class1, Type... path) {
        List<Element> result = new LinkedList<Element>();
        List<Type> tlist = new LinkedList<Type>(Arrays.asList(path));
        if (tlist.size() > 0)
            for (Element node : tree) {
                Type head = tlist.remove(0);
                if (head == null || head.equals(node.type)) {
                    findSub(node, tlist, result);
                }
                tlist.add(0, head);
            }

        return result.toArray((T[]) Array.newInstance(class1, 0));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] findAll(Element master, Class<T> class1, Type... path) {
        List<Element> result = new LinkedList<Element>();
        List<Type> tlist = new LinkedList<Type>(Arrays.asList(path));
        if (!master.type.equals(tlist.get(0)))
            return result.toArray((T[]) Array.newInstance(class1, 0));
        
        tlist.remove(0);
        findSub(master, tlist, result);
        return result.toArray((T[]) Array.newInstance(class1, 0));
    }

    private static void findSub(Element master, List<Type> path, Collection<Element> result) {
        if (path.size() > 0) {
            Type head = path.remove(0);
            if (master instanceof MasterElement) {
                MasterElement nb = (MasterElement) master;
                for (Element candidate : nb.children) {
                    if (head == null || head.equals(candidate.type)) {
                        findSub(candidate, path, result);
                    }
                }
            }
            path.add(0, head);
        } else {
            result.add(master);
        }
    }
    
}