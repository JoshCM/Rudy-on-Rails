using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Stock : InteractiveGameObject, IPlaceableOnSquare
    {
        private Compass alignment;
        private ObservableCollection<Resource> resources;

        public Stock(Guid id, Square square, Compass alignment, List<Resource> resources) : base(square)
        {
            this.id = id;
            this.alignment = alignment;

            // zu testzwecken
            // Resources.Add(resources.FirstOrDefault());
        }
        public Compass Alignment
        {
            get
            {
                return alignment;
            }
            set
            {
                alignment = value;
                NotifyPropertyChanged("Alignment");
            }
        }

        public ObservableCollection<Resource> Resources
        {
            get
            {
                return resources;
            }
            set
            {
                resources = value;
                NotifyPropertyChanged("PlayerResources");
            }
        }
    }
}
