using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Sound
{
    class BackgroundSound:SoundBase
    {
        private const String FILE_PATH = @"..\..\Resources\Sounds\BackgroundMusic.wav";
        public BackgroundSound() : base(FILE_PATH)
        {
        }
    }
}
