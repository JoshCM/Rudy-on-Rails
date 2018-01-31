using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Threading;

namespace RoRClient.Sound
{
    public static class SoundManager
    {
        private static List<SoundBase> sounds = new List<SoundBase>();
        private static bool muted = false;
        public static bool Muted
        {
            get
            {
                return muted;
            }
            set
            {
                muted = value;
            }
        }

        public static void AddSound(SoundBase sound)
        {
            sounds.Add(sound);
        }

        public static void StopSounds()
        {
            foreach(SoundBase sound in sounds)
            {
                sound.Stop();
            }
        }

        public static void PlaySounds()
        {
            foreach(SoundBase sound in sounds)
            {
                sound.ResumePlay();
            }
        }

        public static void DeleteSounds()
        {
            sounds = new List<SoundBase>();
        }
    }
}
