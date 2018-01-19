
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für einen Cart, der einer Schiene zugeordnet ist
    /// Der Cart besitzt mehrere Container (wird erst später implementiert)
    /// </summary>
    public class Cart : InteractiveGameObject, IPlaceableOnRail
    {
        private Compass drivingDirection;
        private int speed;
        private bool isGhostCart;
        private Guid playerId;
        private String onboardResourceImagePath;
        public Resource onboardResource;

        public Cart(Guid id, Guid playerId, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.playerId = playerId;
            this.drivingDirection = drivingDirection;
            this.onboardResource = null;
            this.onboardResourceImagePath= null;

        }
        public Resource OnboardResource
        {
            get
            {
                return onboardResource;
            }
            set
            {
            onboardResource = value;   
            }
        }
        public String OnboardResourceImagePath
        {
            get
            {
                return onboardResourceImagePath;
            }
            set
            {
                onboardResourceImagePath = value;
            }
        
        }
        /*
         * Setzt ImagePfad für OnboardResource in View ein
         * Also beim abladen onboardResource=null und onboardResourceImagePath=null setzen
         * */
        public void updateOnboardResourceImagePath(String newImagePath)
        {
            onboardResourceImagePath = newImagePath;
            NotifyPropertyChanged("OnboardResourceImagePath", this.onboardResourceImagePath, newImagePath);

        }
        public void UpdateOnboardResource(Resource res)
        {
            onboardResource = res;
            NotifyPropertyChanged("OnboardResource", this.onboardResource, res);
        }

        public bool IsGhostCart
        {
            get
            {
                return isGhostCart;
            }
            set
            {
                if (isGhostCart != value)
                {
                    isGhostCart = value;
                    NotifyPropertyChanged("IsGhostCart");
                }
            }
        }

        public Guid PlayerId
        {
            get
            {
                return playerId;
            }
        }

        public Compass DrivingDirection
        {
            get
            {
                return drivingDirection;
            }
            set
            {
                if (drivingDirection != value)
                {
                    drivingDirection = value;
                    NotifyPropertyChanged("DrivingDirection");
                }
            }
        }
      
        public int Speed
        {
            get
            {
                return speed;
            }
            set
            {
                if (speed != value)
                {
                    speed = value;
                    NotifyPropertyChanged("Speed");
                }
            }
        }
    }
}
