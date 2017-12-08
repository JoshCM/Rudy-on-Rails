using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;

namespace RoRClient.Communication.Dispatcher
{
    class QueueDispatcherBase
    {
        /// <summary>
        /// Dummymethode für Funktionszuweisungen von ServerResponses
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="methodName"></param>
        public void CallMethodFromString(string methodName, MessageInformation messageInformation)
        {
            MethodInfo method = this.GetType().GetMethod(methodName);
            method.Invoke(this, new[] {messageInformation});
        }

        public void Dispatch(string request, string message)
        {
            MessageInformation messageInformation = MessageDeserializer.getInstance().Deserialize(message);
            CallMethodFromString("handle" + request, messageInformation);
        }
    }
}
