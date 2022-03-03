package kafka.challenge.sink;

import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class HdfsSink implements java.io.Serializable
{
    public String hdfs_uri= "hdfs://12.0.7.71:8020";
    public String hdfs_path = "/user/spark/delphoi/events4/";

    public void write(List<String> gList)
    {
        String source = "212data" + "hdfssink" + System.currentTimeMillis();
        byte[] bytes = source.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        BufferedWriter br;
        OutputStream os;
        org.apache.hadoop.fs.FileSystem fs;

        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();

        try {
            fs = org.apache.hadoop.fs.FileSystem.get(new URI(hdfs_uri), configuration);
            Path filePath = new Path(hdfs_path + uuid);

            if ( !fs.exists(filePath)) {
                //fs.delete(filePath, true );
                os = fs.create(filePath);
            }
            else {
                os = fs.append(filePath);
            }
            br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );

            Iterator it = gList.iterator();
            while(it.hasNext()) {
                br.write((String)it.next());
                br.newLine();
            }
            br.close();
            fs.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
    }

    public void writePartitioned(List<String> gList, Long epochTime)
    {
        Calendar customCal = Calendar.getInstance();
        customCal.setTimeInMillis(epochTime);

        String year = String.valueOf(customCal.get(Calendar.YEAR));
        String month = String.valueOf(customCal.get(Calendar.MONTH)+1);
        String day = String.valueOf(customCal.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(customCal.get(Calendar.HOUR_OF_DAY));

        this.hdfs_path += "year="+year+"/month="+month+"/day="+day+"/hour="+hour+"/";

        String source = "212data" + "hdfssink" + epochTime;
        byte[] bytes = source.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        BufferedWriter br;
        OutputStream os;
        org.apache.hadoop.fs.FileSystem fs;

        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();

        try {
            fs = org.apache.hadoop.fs.FileSystem.get(new URI(hdfs_uri), configuration);
            Path filePath = new Path(hdfs_path + uuid);

            if ( !fs.exists(filePath)) {
                //fs.delete(filePath, true );
                os = fs.create(filePath);
            }
            else {
                os = fs.append(filePath);
            }
            br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );

            Iterator it = gList.iterator();
            while(it.hasNext()) {
                br.write((String)it.next());
                br.newLine();
            }
            br.close();
            fs.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
    }
}
