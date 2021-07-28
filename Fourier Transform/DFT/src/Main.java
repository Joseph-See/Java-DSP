/*
* Program for calculating the discrete Fourier transform of an input signal array
 */
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException {

        Signals inputSignal = new Signals();
        double[] OutputREX = new double[inputSignal.InputSignal_f32_1kHz_15kHz.length];
        double[] OutputIMX = new double[inputSignal.InputSignal_f32_1kHz_15kHz.length];
        double[] OutputMAG = new double[inputSignal.InputSignal_f32_1kHz_15kHz.length];

        calc_sig_dft(inputSignal.InputSignal_f32_1kHz_15kHz,
                OutputREX,
                OutputIMX,
                inputSignal.InputSignal_f32_1kHz_15kHz.length);

        get_dft_output_mag(OutputREX, OutputIMX, OutputMAG, inputSignal.InputSignal_f32_1kHz_15kHz.length);

        try {
            write("output_rex.dat", OutputREX);
            write("output_imx.dat", OutputIMX);
            write("output_mag.dat", OutputMAG);
            write("input_signal.dat", inputSignal.InputSignal_f32_1kHz_15kHz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Method to calculate the DFT of a signal. Arguments are source signal, destination array for the real components
    * of the DFT, destination array for the imaginary components of the DFT and the integer input signal length.
     */
    public static void calc_sig_dft(double[] sig_src_arr, double[] sig_dest_rex_arr, double[] sig_dest_imx_arr,
                                    int sig_length) {
        int i, j, k;

        for (j = 0; j < sig_length; j++) {
            sig_dest_imx_arr[j] = 0;
            sig_dest_rex_arr[j] = 0;
        }
        for (k = 0; k < sig_length; k++) {
            for (i = 0; i < sig_length; i++) {
                sig_dest_rex_arr[k] = sig_dest_rex_arr[k] + sig_src_arr[i] * Math.cos(2*Math.PI*k*i/sig_length);
                sig_dest_imx_arr[k] = sig_dest_imx_arr[k] - sig_src_arr[i] * Math.sin(2*Math.PI*k*i/sig_length);
            }
        }
    }

    public static void get_dft_output_mag(double[] sig_src_rex_arr, double[] sig_src_imx_arr,
                                          double[] sig_dest_mag_arr, int sig_length) {
        int x;
        for (x = 0; x < sig_length; x++) {
            sig_dest_mag_arr[x] = Math.sqrt(Math.pow(sig_src_rex_arr[x], 2) + Math.pow(sig_src_imx_arr[x], 2));
        }
    }

    public static void write(String filename, double[] x)throws IOException{

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for(int i =0;i<x.length;i++){
            outputWriter.write(Double.toString(x[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }
}