using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Media;

namespace RoRClient.Sound
{
    public class LocoSound
    {
        private System.Media.SoundPlayer player = null;
        private bool isPlaying = false;
        String filePath = @"C:\Users\Joshua Mestre\Desktop\ror\RoRClient\Sound\Sounds\004.wav";

        public LocoSound()
        {
            
        }

        public void stop()
        {
            this.player.Stop();
            this.isPlaying = false;
        }

        public void playInLoop()
        {
            if(this.player != null)
            {
                if (!isPlaying)
                {
                    this.player.PlayLooping();
                    this.isPlaying = true;
                }

            }
        }
        
        public void play()
        {
            if((this.player != null) && !isPlaying)
            {
                this.player.Play();
            }
        }

        public void play(int rate)
        {
            int Rate = 2;
            byte[] B = File.ReadAllBytes(filePath);
            int SampleRate = BitConverter.ToInt32(B, 24) * Rate;
            Array.Copy(BitConverter.GetBytes(SampleRate), 0, B, 24, 4);
            SoundPlayer SP = new SoundPlayer(new MemoryStream(B));
            SP.Play();
        }

    }
}