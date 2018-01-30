using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Media;
using System.IO;

namespace RoRClient.Sound
{
    public class SoundBase
    {
        private SoundPlayer soundPlayer;
        private String filePath;
        private bool disabled;
        private bool looping = false;
        private int rate = 1;
        public int Rate
        {
            get
            {
                return rate;
            }
            set
            {
                if (rate == value) return;
                rate = value;
            }
        }

        public SoundBase(String filePath)
        {
            this.filePath = filePath;
            soundPlayer = new SoundPlayer(filePath);
            SoundManager.AddSound(this);
        }

        public void Stop()
        {
            soundPlayer.Stop();
            disabled = true;
        }

        public void PlayInLoop()
        {
            if (!disabled)
            {
                looping = true;
                byte[] B = File.ReadAllBytes(filePath);
                int SampleRate = BitConverter.ToInt32(B, 24) * rate;
                Array.Copy(BitConverter.GetBytes(SampleRate), 0, B, 24, 4);
                soundPlayer = new SoundPlayer(new MemoryStream(B));
                soundPlayer.PlayLooping();
            }
            

        }

        public void Play()
        {
            if (!disabled)
            {
                byte[] B = File.ReadAllBytes(filePath);
                int SampleRate = BitConverter.ToInt32(B, 24) * rate;
                Array.Copy(BitConverter.GetBytes(SampleRate), 0, B, 24, 4);
                soundPlayer = new SoundPlayer(new MemoryStream(B));
                soundPlayer.Play();
            }
        }

        public void ResumePlay()
        {
            disabled = false;
            if (looping)
            {
                PlayInLoop();
            }
            else
            {
                Play();
            }

        }
    }
}
