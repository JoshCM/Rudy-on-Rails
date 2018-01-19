using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using RoRClient.Models.Session;

namespace RoRClient.ViewModels.Game
{
    public class GhostLocoGameViewModel : LocoGameViewModel
    {
        private GhostLoco ghostLoco;
        private bool belongsToOwnPlayer;

        public GhostLocoGameViewModel(GhostLoco loco) : base(loco)
        {
            this.ghostLoco = loco;

            if(ghostLoco.PlayerId == GameSession.GetInstance().OwnPlayer.Id)
            {
                BelongsToOwnPlayer = true;
            }
        }

        public bool BelongsToOwnPlayer
        {
            get
            {
                return belongsToOwnPlayer;
            }
            set
            {
                belongsToOwnPlayer = value;
                OnPropertyChanged("BelongsToOwnPlayer");
            }
        }
    }
}
