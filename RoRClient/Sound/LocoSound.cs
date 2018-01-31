using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Media;

namespace RoRClient.Sound
{
    public class LocoSound:SoundBase
    {
        private const String SOUND_PATH = "..\\..\\Resources\\Sounds\\tschuk.wav";
        public LocoSound():base(SOUND_PATH)
        {   
        }
    }
}