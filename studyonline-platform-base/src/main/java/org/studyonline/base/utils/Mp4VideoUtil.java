package org.studyonline.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp4VideoUtil extends VideoUtil {

    String ffmpeg_path = "";// ffmpeg installation location
    String video_path = ""; // Video path
    String mp4_name = ""; //The name of the exported video
    String mp4folder_path = ""; //Path to export video
    public Mp4VideoUtil(String ffmpeg_path, String video_path, String mp4_name, String mp4folder_path){
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.mp4_name = mp4_name;
        this.mp4folder_path = mp4folder_path;
    }

    //Clear generated mp4
    private void clear_mp4(String mp4_path){
        //Delete the originally generated m3u8 and ts files
        File mp4File = new File(mp4_path);
        if(mp4File.exists() && mp4File.isFile()){
            mp4File.delete();
        }
    }
    /**
     * Video encoding, generate mp4 files
     * @return Success is returned on success, and console log is returned on failure.
     */
    public String generateMp4(){
        //Clear generated mp4
        clear_mp4(mp4folder_path);
        /*
        ffmpeg.exe -i  lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
         */
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpeg_path);
        commend.add("-i");
        commend.add(video_path);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-y");//Overwrite output file
        commend.add("-s");
        commend.add("1280x720");
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-b:a");
        commend.add("63k");
        commend.add("-b:v");
        commend.add("753k");
        commend.add("-r");
        commend.add("18");
        commend.add(mp4folder_path  );
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //Combine the standard input stream and the error input stream and read information through the standard input process
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
//        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path + mp4_name);
        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path);
        if(!check_video_time){
            return outstring;
        }else{
            return "success";
        }
    }

    public static void main(String[] args) throws IOException {
        //ffmpeg path
        String ffmpeg_path = "D:\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe";// ffmpeg installation location
        //The path of the source avi video
        String video_path = "D:\\Download\\test1.avi";
        //The name of the converted mp4 file
        String mp4_name = "test1.mp4";
        //The path of the converted mp4 file
        String mp4_path = "D:\\develop\\bigfile_test\\";
        //Create tool object
        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4_path);
        //Start video conversion, success will be returned
        String s = videoUtil.generateMp4();
        System.out.println(s);
    }
}